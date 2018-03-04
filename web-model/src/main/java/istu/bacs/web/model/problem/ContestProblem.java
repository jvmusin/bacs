package istu.bacs.web.model.problem;

import istu.bacs.db.contest.Contest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblem {
    private String index;
    private String name;
    private int contestId;
    private int timeLimitMillis;
    private int memoryLimitBytes;
    private String statementUrl;

    public static ContestProblem fromDb(istu.bacs.db.contest.ContestProblem contestProblem) {
        Contest contest = contestProblem.getContest();
        istu.bacs.db.problem.Problem problem = contestProblem.getProblem();

        return new ContestProblem(
                contestProblem.getProblemIndex(),
                problem.getName(),
                contest.getContestId(),
                problem.getTimeLimitMillis(),
                problem.getMemoryLimitBytes(),
                problem.getStatementUrl()
        );
    }
}