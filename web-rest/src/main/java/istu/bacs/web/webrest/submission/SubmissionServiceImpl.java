package istu.bacs.web.webrest.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public Mono<Submission> findById(int submissionId) {
        return Mono.justOrEmpty(submissionRepository.findById(submissionId));
    }

    @Override
    public Flux<Submission> findAll() {
        return Flux.fromIterable(submissionRepository.findAll());
    }
}
