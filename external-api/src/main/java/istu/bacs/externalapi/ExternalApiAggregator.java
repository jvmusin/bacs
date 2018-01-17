package istu.bacs.externalapi;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;

import java.util.List;

public interface ExternalApiAggregator {
    List<Problem> getAllProblems();
    void submit(List<Submission> submissions);
    void updateSubmissionDetails(List<Submission> submissions);
}