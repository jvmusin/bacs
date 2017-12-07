package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.util.List;
import java.util.Set;

public interface ExternalApi {
    Problem getProblem(String problemId);
    List<Problem> getAllProblems();
    void submit(Submission submission);
    void submit(List<Submission> submissions);
    void updateSubmissionDetails(List<Submission> submissions);
    void updateProblemDetails(List<Problem> problems);

    Set<Language> getSupportedLanguages();

    String getResourceName();
}