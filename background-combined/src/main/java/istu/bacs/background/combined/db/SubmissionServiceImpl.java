package istu.bacs.background.combined.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.Verdict;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    @Transactional
    public List<Submission> findAllByVerdict(Verdict verdict) {
        return submissionRepository.findAllByResultVerdict(verdict);
    }

    @Override
    @Transactional
    public List<Submission> findAllByIds(List<Integer> ids) {
        return submissionRepository.findAllById(ids);
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }
}