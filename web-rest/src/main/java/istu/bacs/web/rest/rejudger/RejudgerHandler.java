package istu.bacs.web.rest.rejudger;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.web.model.Rejudge;
import istu.bacs.web.rest.contest.ContestService;
import istu.bacs.web.rest.submission.SubmissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class RejudgerHandler {

    private final ContestService contestService;
    private final SubmissionService submissionService;

    public RejudgerHandler(ContestService contestService, SubmissionService submissionService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
    }

    @Bean
    public RouterFunction<ServerResponse> rejudgerRouter() {
        return route(PUT("/rejudger"), this::rejudge);
    }

    private Mono<ServerResponse> rejudge(ServerRequest request) {
        return request.bodyToMono(Rejudge.class)
                .flatMapMany(this::findAllContestProblems)
                .transform(this::findSubmissions)
                //                .transform(REJUDGE_SUBMISSIONS)
                .map(Submission::getSubmissionId)
                .collectList()
                .transform(submissions -> ok().body(submissions, new ParameterizedTypeReference<List<Integer>>() {
                }));
    }

    private Flux<ContestProblem> findAllContestProblems(Rejudge rejudge) {
        return Mono.just(rejudge).flatMapMany(r -> isEmpty(r.getProblemIndex())
                ? getProblems(r.getContestId())
                : Flux.just(ContestProblem.withContestIdAndProblemIndex(r.getContestId(), r.getProblemIndex())));
    }

    private Flux<ContestProblem> getProblems(int contestId) {
        return contestService.findById(contestId)
                .map(Contest::getProblems)
                .flatMapMany(Flux::fromIterable);
    }

    private Flux<Submission> findSubmissions(Flux<ContestProblem> problems) {
        return problems
                .map(ContestProblem::getContestProblemId)
                .flatMap(submissionService::findAllByContestProblemAndChecked);
    }
}