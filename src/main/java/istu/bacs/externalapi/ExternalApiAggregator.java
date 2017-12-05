package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.util.List;
import java.util.Set;

public interface ExternalApiAggregator {
    Problem getProblem(String problemId);
    void submit(boolean pretestsOnly, Submission submission);
    void submitAll(List<Submission> submissions, boolean pretestsOnly);
    void updateSubmissions(List<Submission> submissions);
    void updateProblems(List<Problem> problems);

    Set<Language> getSupportedLanguages(String resourceName);
}