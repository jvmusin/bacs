package istu.bacs.web.model.get;

import lombok.Value;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Contest {
    int id;
    String name;
    String startTime;
    String finishTime;
    Problem[] problems;

    public static Contest fromDb(istu.bacs.db.contest.Contest contest) {
        return new Contest(
                contest.getContestId(),
                contest.getName(),
                formatDateTime(contest.getStartTime()),
                formatDateTime(contest.getFinishTime()),
                contest.getProblems().stream().map(Problem::fromDb).toArray(Problem[]::new)
        );
    }
}