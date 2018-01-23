package istu.bacs.background.standingsbuilder;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.standingsapi.dto.ContestantRowDto;
import istu.bacs.standingsapi.dto.ProblemSolvingResultDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

    public ContestantRowDto toDto() {
        ContestantRowDto row = new ContestantRowDto();

        row.setUsername(contestant.getUsername());
        row.setPlace(place);
        row.setSolvedCount(solvedCount);
        row.setPenalty(penalty);
        row.setResults(progressByProblem.entrySet().stream()
                .map(e -> {
                    ProblemSolvingResultDto result = new ProblemSolvingResultDto();

                    result.setProblemIndex(e.getKey().getProblemIndex());

                    SolvingResult res = e.getValue().getResult();
                    result.setSolved(res.isSolved());
                    result.setFailTries(res.getFailTries());
                    result.setPenalty(res.getPenalty());

                    return result;
                })
                .collect(toList()));

        return row;
    }
}