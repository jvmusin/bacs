package istu.bacs.standings;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.model.get.ProblemSolvingResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static istu.bacs.standings.SolvingResult.TRY_PENALTY_MINUTES;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Data
public class ContestantRow {

    private final User contestant;
    private final Map<ContestProblem, ProblemProgress> progressByProblem;

    private int solvedCount;
    private int penalty;

    private int place;

    ContestantRow(User contestant, List<ContestProblem> contestProblems) {
        this.contestant = contestant;
        progressByProblem = contestProblems.stream()
                .collect(toMap(
                        identity(),
                        p -> new ProblemProgress()
                ));
    }

    private static ProblemSolvingResult createProblemSolvingResult(Map.Entry<ContestProblem, ProblemProgress> e) {
        String problemIndex = e.getKey().getProblemIndex();

        SolvingResult res = e.getValue().getResult();
        boolean solved = res.isSolved();
        int failTries = res.getFailTries();

        int solvedAt = res.getPenalty();
        if (res.isSolved()) solvedAt -= res.getFailTries() * TRY_PENALTY_MINUTES;

        return new ProblemSolvingResult(problemIndex, solved, failTries, solvedAt);
    }

    public void update(Submission submission) {
        ProblemProgress progress = progressByProblem.get(submission.getContestProblem());

        SolvingResult wasResult = progress.getResult();
        if (wasResult.isSolved()) {
            solvedCount--;
            penalty -= wasResult.getPenalty();
        }

        progress.update(submission);

        SolvingResult result = progress.getResult();
        if (result.isSolved()) {
            solvedCount++;
            penalty += result.getPenalty();
        }
    }

    public istu.bacs.web.model.get.ContestantRow toDto() {
        List<ProblemSolvingResult> results = progressByProblem.entrySet()
                .stream()
                .map(ContestantRow::createProblemSolvingResult)
                .filter(r -> r.isSolved() || r.getFailTries() > 0)
                .collect(toList());

        return new istu.bacs.web.model.get.ContestantRow(
                istu.bacs.web.model.User.fromDb(contestant),
                place,
                solvedCount,
                penalty,
                results
        );
    }
}