package istu.bacs.web.rest.submission;

import istu.bacs.db.submission.Submission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubmissionService {
    Mono<Submission> findById(Mono<Integer> submissionId);
    Flux<Submission> findAll();
}