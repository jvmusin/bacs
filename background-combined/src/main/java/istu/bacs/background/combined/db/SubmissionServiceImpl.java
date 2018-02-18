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
        List<Submission> submissions = submissionRepository.findAllByResultVerdict(verdict);
        initializeSubmissions(submissions);
        return submissions;
    }

    @Override
    @Transactional
    public List<Submission> findAllByIds(List<Integer> ids) {
        List<Submission> submissions = submissionRepository.findAllById(ids);
        initializeSubmissions(submissions);
        return submissions;
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }

    private void initializeSubmission(Submission submission) {
        if (submission != null)
            System.out.println(submission.getContest().getProblems().size());
    }

    private void initializeSubmissions(Iterable<Submission> submissions) {
        submissions.forEach(this::initializeSubmission);
    }
}