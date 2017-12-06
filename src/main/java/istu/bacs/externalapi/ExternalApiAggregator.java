package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.util.List;
import java.util.Set;

public interface ExternalApiAggregator {
    Problem getProblem(String problemId);
    void submit(Submission submission, boolean pretestsOnly);
    void submitAll(List<Submission> submissions, boolean pretestsOnly);
    void updateSubmissionDetails(List<Submission> submissions);
    void updateProblemDetails(List<Problem> problems);

    Set<Language> getSupportedLanguages(String resourceName);
}