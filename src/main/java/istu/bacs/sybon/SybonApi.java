package istu.bacs.sybon;

import istu.bacs.model.Problem;
import istu.bacs.model.Submission;

import java.net.URI;

public interface SybonApi {
    Problem getProblem(int id);
    URI getStatementUrl(int problemId);

    void submit(Submission submission, boolean pretestsOnly);

    //unusable
    SybonProblemCollection[] getProblemCollections(int offset, int limit);
    SybonProblemCollection getProblemCollection(int id);
    SybonCompiler[] getCompilers();

    Submission.SubmissionResult[] getSubmissionResults(int... ids);
}