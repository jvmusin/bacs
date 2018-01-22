package istu.bacs.standingsbuilder.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;

import java.util.List;

public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
    }

    @Override
    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }
}