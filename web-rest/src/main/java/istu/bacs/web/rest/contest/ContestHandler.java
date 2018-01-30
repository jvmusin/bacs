package istu.bacs.web.rest.contest;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import istu.bacs.rabbit.QueueName;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.web.model.*;
import istu.bacs.web.rest.problem.ProblemService;
import istu.bacs.web.rest.submission.SubmissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ContestHandler {

    private final ContestService contestService;
    private final SubmissionService submissionService;
    private final ProblemService problemService;
    private final RabbitService rabbitService;

    public ContestHandler(ContestService contestService, SubmissionService submissionService, ProblemService problemService, RabbitService rabbitService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.problemService = problemService;
        this.rabbitService = rabbitService;
    }

    @Bean
    public RouterFunction<ServerResponse> contestRouter() {
        return route(GET("/contests"), this::getAllContests)
                .andRoute(GET("/contests/{contestId}"), this::getContest)
                .andRoute(GET("/contests/{contestId}/problems"), this::getContestProblems)
                .andRoute(GET("/contests/{contestId}/problems/{problemIndex}"), this::getContestProblem)
                .andRoute(GET("/contests/{contestId}/problems/{problemIndex}/submissions"), this::getContestProblemSubmissions)
                .andRoute(GET("/contests/{contestId}/submissions"), this::getContestSubmissions)
                .andRoute(GET("/contests/{contestId}/standings"), this::getContestStandings)
                .andRoute(POST("/contests/{contestId}/submissions"), this::submitSolution)
                .andRoute(POST("/contests/{contestId}"), this::createContest)
                .andRoute(PUT("/contests/{contestId}"), this::createContest)
                .andRoute(DELETE("/contests/{contestId}"), this::deleteContest);
    }

    private Mono<ServerResponse> getAllContests(@SuppressWarnings("unused") ServerRequest request) {
        return ok().body(
                contestService.findAll().transform(Contest::fromDb),
                Contest.class
        );
    }

    private Mono<ServerResponse> getContest(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(contestId)
                .transform(contestService::findById)
                .transform(Contest::fromDb)
                .transform(contest -> ok().body(contest, Contest.class));
    }

    private Mono<ServerResponse> getContestProblems(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(contestId)
                .transform(contestService::findById)
                .map(istu.bacs.db.contest.Contest::getProblems)
                .map(Flux::fromIterable)
                .map(Problem::fromDbContestProblems)
                .flatMap(problems -> ok().body(problems, Problem.class));
    }

    private Mono<ServerResponse> getContestProblem(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));
        String problemIndex = request.pathVariable("problemIndex");

        return Mono.just(contestId)
                .transform(contestService::findById)
                .map(contest -> contest.getProblem(problemIndex))
                .transform(Problem::fromDbContestProblem)
                .transform(problem -> ok().body(problem, Problem.class));
    }

    private Mono<ServerResponse> getContestProblemSubmissions(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));
        String problemIndex = request.pathVariable("problemIndex");

        return Mono.just(submissionService.findAllByContestAndProblemIndex(Mono.just(contestId), Mono.just(problemIndex)))
                .map(Submission::fromDb)
                .flatMap(submissions -> ok().body(submissions, Submission.class));
    }

    private Mono<ServerResponse> getContestSubmissions(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(submissionService.findAllByContest(Mono.just(contestId)))
                .map(Submission::fromDb)
                .flatMap(submissions -> ok().body(submissions, Submission.class));
    }

    private Mono<ServerResponse> getContestStandings(@SuppressWarnings("unused") ServerRequest request) {
        //TODO Implement it
        return null;
    }

    private Mono<ServerResponse> submitSolution(ServerRequest request) {
        return request.bodyToMono(SubmitSolution.class)
                .map(SubmitSolution::toDb)
                .zipWith(request.principal())
                .doOnNext(t -> t.getT1().setAuthor((User) t.getT2()))
                .map(Tuple2::getT1)
                .transform(submissionService::save)
                .doOnNext(submission -> rabbitService.send(QueueName.SCHEDULED_SUBMISSIONS, submission))
                .map(istu.bacs.db.submission.Submission::getSubmissionId)
                .transform(submissionId -> ok().body(submissionId, Integer.class));
    }

    private Mono<ServerResponse> createContest(ServerRequest request) {
        return request.bodyToMono(EditContest.class)
//                .transform(ec -> contestService.findById(ec.map(EditContest::getId))
//                        .transform(c -> Mono.<EditContest>error(new RuntimeException("Such contest is already created. If you want to edit it, use PUT method.")))
//                        .switchIfEmpty(ec))
                .map(this::createContest)
                .transform(contestService::save)
                .transform(contest -> ok().build());
    }

    private istu.bacs.db.contest.Contest createContest(EditContest ec) {
        istu.bacs.db.contest.Contest contest = istu.bacs.db.contest.Contest.builder()
                .contestId(ec.getId())
                .name(ec.getName())
                .startTime(WebModelUtils.parseDateTime(ec.getStartTime()))
                .finishTime(WebModelUtils.parseDateTime(ec.getFinishTime()))
                .build();

        List<ContestProblem> problems = Flux.fromArray(ec.getProblems())
                .map(problem -> {
                    Mono<istu.bacs.db.problem.Problem> dbProblem = problemService.findById(Mono.just(problem.getProblemId()));
                    ContestProblem cp = ContestProblem.withContestIdAndProblemIndex(ec.getId(), problem.getProblemIndex());
                    cp.setProblem(dbProblem.block());
                    cp.setContest(contest);
                    return cp;
                }).collectList().block();
        contest.setProblems(problems);

        problems.forEach(problem -> problem.setContest(contest));

        return contest;
    }

    private Mono<ServerResponse> deleteContest(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(contestId)
                .map(id -> istu.bacs.db.contest.Contest.builder().contestId(contestId).build())
                .transform(contestService::delete)
                .transform(contest -> ok().build());
    }
}