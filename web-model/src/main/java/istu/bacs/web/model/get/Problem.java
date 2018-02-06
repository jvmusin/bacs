package istu.bacs.web.model.get;

import istu.bacs.db.contest.Contest;
import lombok.Value;

@Value
public class Problem {
    String index;
    String name;
    int contestId;
    int timeLimitMillis;
    int memoryLimitBytes;
    String statementUrl;

    public static Problem fromDb(istu.bacs.db.contest.ContestProblem contestProblem) {
        Contest contest = contestProblem.getContest();
        istu.bacs.db.problem.Problem problem = contestProblem.getProblem();

        return new Problem(
                contestProblem.getProblemIndex(),
                problem.getName(),
                contest.getContestId(),
                problem.getTimeLimitMillis(),
                problem.getMemoryLimitBytes(),
                problem.getStatementUrl()
        );
    }
}