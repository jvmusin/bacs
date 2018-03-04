package istu.bacs.web.model.contest;

import istu.bacs.web.model.problem.ContestProblem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contest {
    private int id;
    private String name;
    private String startTime;
    private String finishTime;
    private ContestProblem[] problems;

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