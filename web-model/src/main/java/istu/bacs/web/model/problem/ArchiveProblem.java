package istu.bacs.web.model.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveProblem {

    private ArchiveProblemId problemId;
    private String name;
    private int timeLimitMillis;
    private int memoryLimitBytes;
    private String statementUrl;

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