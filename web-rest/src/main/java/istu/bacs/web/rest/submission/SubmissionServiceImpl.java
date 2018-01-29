package istu.bacs.web.rest.submission;

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
    public Mono<Submission> findById(Mono<Integer> submissionId) {
        return submissionId
                .map(submissionRepository::findById)
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    public Flux<Submission> findAll() {
        return Flux.fromIterable(submissionRepository.findAll());
    }
}
