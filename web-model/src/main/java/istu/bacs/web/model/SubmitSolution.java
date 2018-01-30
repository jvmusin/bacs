package istu.bacs.web.model;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Language;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.submission.Verdict;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class SubmitSolution {
    int contestId;
    String problemIndex;
    Language language;
    String solution;

    public Submission toDb() {
        return Submission.builder()
                .contestProblem(ContestProblem.withContestIdAndProblemIndex(contestId, problemIndex))
                .language(language)
                .solution(solution)
                .created(LocalDateTime.now())
                .result(SubmissionResult.builder().verdict(Verdict.SCHEDULED).build())
                .build();
    }
}