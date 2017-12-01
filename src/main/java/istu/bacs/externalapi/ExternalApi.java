package istu.bacs.externalapi;

import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.net.URI;
import java.util.List;

public interface ExternalApi {
    Problem getProblem(String problemId);
    URI getStatementUrl(String problemId);
    void submit(boolean pretestsOnly, Submission submission);
    void submit(boolean pretestsOnly, List<Submission> submissions);
    void updateSubmissionResults(List<Submission> submissions);
    void updateProblemDetails(List<Problem> problems);

    String getResourceName();
}