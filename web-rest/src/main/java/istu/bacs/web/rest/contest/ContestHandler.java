package istu.bacs.web.rest.contest;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import istu.bacs.externalapi.ExternalApi;
import istu.bacs.rabbit.QueueName;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.standingsapi.StandingsService;
import istu.bacs.web.model.*;
import istu.bacs.web.rest.problem.ProblemService;
import istu.bacs.web.rest.submission.SubmissionService;
import istu.bacs.web.security.JWTAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ContestHandler {

    private final ContestService contestService;
    private final SubmissionService submissionService;
    private final ProblemService problemService;
    private final StandingsService standingsService;
    private final ExternalApi externalApi;
    private final RabbitService rabbitService;

    public ContestHandler(ContestService contestService, SubmissionService submissionService, ProblemService problemService, StandingsService standingsService, ExternalApi externalApi, RabbitService rabbitService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.problemService = problemService;
        this.standingsService = standingsService;
        this.externalApi = externalApi;
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
                .andRoute(POST("/contests"), this::createContest)
                .andRoute(PUT("/contests/{contestId}"), request -> {
                    throw new RuntimeException("Not implemented yet");
                })
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
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(contestId)
                .transform(standingsService::getStandings)
                .transform(standings -> ok().body(standings, Standings.class));
    }

    private Mono<ServerResponse> submitSolution(ServerRequest request) {
        return request.bodyToMono(SubmitSolution.class)
                .map(SubmitSolution::toDb)
                .zipWith(request.principal())
                .doOnNext(t -> t.getT1().setAuthor(((JWTAuthenticationToken) t.getT2()).getPrincipal()))
                .map(Tuple2::getT1)
                .transform(submissionService::save)
                .map(istu.bacs.db.submission.Submission::getSubmissionId)
                .transform(submissionService::findById)
                .doOnNext(externalApi::submit)
                .map(istu.bacs.db.submission.Submission::getSubmissionId)
                .doOnNext(submissionId -> rabbitService.send(QueueName.SCHEDULED_SUBMISSIONS, submissionId))
                .transform(submissionId -> ok().body(submissionId, Integer.class));
    }

    private Mono<ServerResponse> createContest(ServerRequest request) {
        return request.bodyToMono(EditContest.class)
                .flatMap(ec -> Mono.just(
                        istu.bacs.db.contest.Contest.builder()
                                .name(ec.getName())
                                .startTime(WebModelUtils.parseDateTime(ec.getStartTime()))
                                .finishTime(WebModelUtils.parseDateTime(ec.getFinishTime()))
                                .problems(emptyList())
                                .build())
                        .transform(contestService::save)
                        .doOnNext(c -> {
                            List<ContestProblem> problems = Flux.fromArray(ec.getProblems())
                                    .map(problem -> {
                                        Mono<istu.bacs.db.problem.Problem> dbProblem = problemService.findById(Mono.just(problem.getProblemId()));
                                        ContestProblem cp = ContestProblem.withContestIdAndProblemIndex(c.getContestId(), problem.getProblemIndex());
                                        cp.setProblem(dbProblem.block());
                                        cp.setContest(c);
                                        return cp;
                                    })
                                    .collectList().block();
                            c.setProblems(problems);
                            problems.forEach(problem -> problem.setContest(c));
                        })
                        .transform(contestService::save)
                )
                .map(istu.bacs.db.contest.Contest::getContestId)
                .transform(contestId -> ok().body(contestId, Integer.class));
    }

    private Mono<ServerResponse> deleteContest(ServerRequest request) {
        int contestId = Integer.parseInt(request.pathVariable("contestId"));

        return Mono.just(contestId)
                .map(id -> istu.bacs.db.contest.Contest.builder().contestId(id).build())
                .transform(contestService::delete)
                .flatMap(contest -> ok().build());
    }
}