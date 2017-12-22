package istu.bacs.externalapi;

import istu.bacs.submission.Language;
import istu.bacs.problem.Problem;
import istu.bacs.submission.Submission;

import java.util.List;
import java.util.Set;

public interface ExternalApiAggregator {
    Problem getProblem(String problemId);
    List<Problem> getAllProblems();
    void submit(Submission submission);
    void submitAll(List<Submission> submissions);
    void updateSubmissionDetails(List<Submission> submissions);
    void updateProblemDetails(List<Problem> problems);

    Set<Language> getSupportedLanguages(String resourceName);
}