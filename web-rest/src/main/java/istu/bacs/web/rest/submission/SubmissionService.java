package istu.bacs.web.rest.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubmissionService {
    Mono<Submission> findById(Mono<Integer> submissionId);
    Flux<Submission> findAll();
    Flux<Submission> findAllByContest(Mono<Integer> contestId);
    Flux<Submission> findAllByContestAndProblemIndex(Mono<Integer> contestId, Mono<String> problemIndex);
    Flux<Submission> findAllByContestProblemAndChecked(String contestProblemId);
    Flux<Submission> findAllByContestProblem(Flux<ContestProblem> problems);
    Mono<Submission> save(Mono<Submission> submission);
}