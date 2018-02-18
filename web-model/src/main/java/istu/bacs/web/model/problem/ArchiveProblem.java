package istu.bacs.web.model.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemId;
import lombok.Value;

@Value
public class ArchiveProblem {

    ArchiveProblemId problemId;
    String name;
    int timeLimitMillis;
    int memoryLimitBytes;
    String statementUrl;

    public static ArchiveProblem fromDb(Problem problem) {
        ProblemId id = problem.getProblemId();
        return new ArchiveProblem(
                new ArchiveProblemId(id.getResourceName().name(), id.getResourceProblemId()),
                problem.getName(),
                problem.getTimeLimitMillis(),
                problem.getMemoryLimitBytes(),
                problem.getStatementUrl()
        );
    }
}