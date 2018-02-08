package istu.bacs.standings.db;

import istu.bacs.db.submission.Submission;

import java.util.List;

public interface SubmissionService {
    Submission findById(int submissionId);
    List<Submission> findAll();
}