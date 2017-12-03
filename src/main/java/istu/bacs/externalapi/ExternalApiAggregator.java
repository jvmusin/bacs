package istu.bacs.externalapi;

import istu.bacs.model.Contest;
import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.net.URI;
import java.util.List;
import java.util.Set;

public interface ExternalApiAggregator {
    Problem getProblem(String problemId);
    URI getStatementUrl(String problemId);
    void submit(boolean pretestsOnly, Submission submission);
    void submit(boolean pretestsOnly, List<Submission> submissions);
    void updateSubmissionResults(List<Submission> submissions);
    void updateProblemDetails(List<Problem> problems);
    void updateContest(Contest contest);

    Set<Language> getSupportedLanguages(String resourceName);
}