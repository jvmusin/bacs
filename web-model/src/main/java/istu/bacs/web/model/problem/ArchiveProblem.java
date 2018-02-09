package istu.bacs.web.model.problem;

import istu.bacs.db.problem.Problem;
import lombok.Value;

@Value
public class ArchiveProblem {

    ArchiveProblemId problemId;
    String name;
    int timeLimitMillis;
    int memoryLimitBytes;
    String statementUrl;

    public static ArchiveProblem fromDb(Problem problem) {
        return new ArchiveProblem(
                new ArchiveProblemId(problem.getResourceName(), problem.getRawProblemId()),
                problem.getName(),
                problem.getTimeLimitMillis(),
                problem.getMemoryLimitBytes(),
                problem.getStatementUrl()
        );
    }
}