package istu.bacs.standings;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import lombok.Data;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static istu.bacs.db.submission.Verdict.*;
import static istu.bacs.standings.SolvingResult.notSolved;
import static istu.bacs.standings.SolvingResult.solved;
import static java.util.Comparator.comparing;

@Data
public class ProblemProgress {

    private static final Set<Verdict> ignoredVerdicts = EnumSet.of(COMPILE_ERROR, PENDING, SERVER_ERROR);

    private final List<Submission> submissions = new ArrayList<>();

    private SolvingResult result = notSolved(0);

    public void update(Submission newSubmission) {
        submissions.remove(newSubmission);
        submissions.add(newSubmission);
        submissions.sort(comparing(Submission::getSubmissionId));

        int failTries = 0;
        for (Submission submission : submissions) {
            if (ignoredVerdicts.contains(submission.getVerdict())) continue;
            if (submission.getVerdict() == ACCEPTED) {
                result = solved(failTries, submission);
                return;
            } else {
                failTries++;
            }
        }

        result = notSolved(failTries);
    }
}