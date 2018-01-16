package istu.bacs.web.externalapi;


import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;

import java.util.List;

public interface ExternalApiAggregator {
    List<Problem> getAllProblems();
    void submit(Submission submission);
    void submit(List<Submission> submissions);
    void updateSubmissionDetails(List<Submission> submissions);
}