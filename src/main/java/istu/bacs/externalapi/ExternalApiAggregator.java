package istu.bacs.externalapi;

import istu.bacs.problem.Problem;
import istu.bacs.submission.Submission;

import java.util.List;

public interface ExternalApiAggregator {
    List<Problem> getAllProblems();
    void submit(Submission submission);
    void updateSubmissionDetails(List<Submission> submissions);
}