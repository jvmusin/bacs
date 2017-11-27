package istu.bacs.sybon;

import java.net.URI;

public interface SybonApi {
    SybonProblem getProblem(int id);
    URI getStatementUrl(int problemId);
    SybonProblemCollection[] getProblemCollections(int offset, int limit);
    SybonProblemCollection getProblemCollection(int id);
    SybonCompiler[] getCompilers();
    SybonSubmitResult[] getSubmits(String ids);
}