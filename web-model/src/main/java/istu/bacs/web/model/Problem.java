package istu.bacs.web.model;

import lombok.Value;

@Value
public class Problem {
    String index;
    String name;
    int contestId;
    int timeLimitMillis;
    int memoryLimitBytes;

    public static Problem fromDb(istu.bacs.db.contest.ContestProblem problem) {
        return new Problem(
                problem.getProblemIndex(),
                problem.getProblem().getName(),
                problem.getContest().getContestId(),
                problem.getProblem().getTimeLimitMillis(),
                problem.getProblem().getMemoryLimitBytes()
        );
    }
}