package istu.bacs.standings.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;

import javax.transaction.Transactional;
import java.util.List;

public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    @Transactional
    public Submission findById(int submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElse(null);
        initializeSubmission(submission);
        return submission;
    }

    @Override
    @Transactional
    public List<Submission> findAll() {
        List<Submission> submissions = submissionRepository.findAll();
        initializeSubmissions(submissions);
        return submissions;
    }

    private void initializeSubmission(Submission submission) {
        if (submission != null) {
            //noinspection ResultOfMethodCallIgnored
            submission.getContest();
        }
    }

    private void initializeSubmissions(Iterable<Submission> submissions) {
        submissions.forEach(this::initializeSubmission);
    }
}