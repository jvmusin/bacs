package istu.bacs.background.combined.db;

import istu.bacs.db.submission.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionResultRepository submissionResultRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
    }

    @Override
    @Transactional
    public List<Submission> findAllByVerdict(Verdict verdict) {
        return submissionResultRepository.findAllByVerdict(verdict).stream()
                .map(SubmissionResult::getSubmission)
                .collect(toList());
    }

    @Override
    public List<Submission> findAllByIds(List<Integer> ids) {
        return submissionRepository.findAllById(ids);
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }
}