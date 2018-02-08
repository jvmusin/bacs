package istu.bacs.web.model.contest;

import istu.bacs.web.model.problem.ContestProblem;
import lombok.Value;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Contest {
    int id;
    String name;
    String startTime;
    String finishTime;
    ContestProblem[] problems;

    public static Contest fromDb(istu.bacs.db.contest.Contest contest) {
        return new Contest(
                contest.getContestId(),
                contest.getName(),
                formatDateTime(contest.getStartTime()),
                formatDateTime(contest.getFinishTime()),
                contest.getProblems().stream().map(ContestProblem::fromDb).toArray(ContestProblem[]::new)
        );
    }
}