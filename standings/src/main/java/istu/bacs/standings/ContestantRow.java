package istu.bacs.standings;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Data
public class ContestantRow {

    private final User contestant;
    private final Map<ContestProblem, ProblemProgress> progressByProblem;

    private int solvedCount;
    private int penalty;

    private int place;

    public ContestantRow(User contestant, List<ContestProblem> contestProblems) {
        this.contestant = contestant;
        progressByProblem = contestProblems.stream()
                .collect(toMap(
                        identity(),
                        p -> new ProblemProgress()
                ));
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
}