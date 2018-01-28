package istu.bacs.web.webrest.submission;

import istu.bacs.db.submission.Submission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubmissionService {
    Mono<Submission> findById(int submissionId);
    Flux<Submission> findAll();
}