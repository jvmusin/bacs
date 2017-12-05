package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.net.URI;
import java.util.List;
import java.util.Set;

public interface ExternalApi {
    Problem getProblem(String problemId);
    void submit(boolean pretestsOnly, Submission submission);
    void submit(boolean pretestsOnly, List<Submission> submissions);
    void updateSubmissions(List<Submission> submissions);
    void updateProblems(List<Problem> problems);

    Set<Language> getSupportedLanguages();

    String getResourceName();
}