package istu.bacs.background.standingsbuilder;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.model.ProblemSolvingResult;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

import static istu.bacs.background.standingsbuilder.SolvingResult.TRY_PENALTY_MINUTES;
import static java.util.function.Function.identity;
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

    public istu.bacs.web.model.ContestantRow toDto() {

        return new istu.bacs.web.model.ContestantRow(
                istu.bacs.web.model.User.fromDb(contestant),
                place,
                solvedCount,
                penalty,
                Flux.fromIterable(progressByProblem.entrySet()).map(e -> {

                    SolvingResult res = e.getValue().getResult();
                    int solvedAt = res.getPenalty();
                    if (res.isSolved()) solvedAt -= res.getFailTries() * TRY_PENALTY_MINUTES;

                    return new ProblemSolvingResult(
                            e.getKey().getProblemIndex(),
                            res.isSolved(),
                            res.getFailTries(),
                            solvedAt
                    );
                }).collectList().block()
        );
    }
}