package istu.bacs.externalapi;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.submission.Verdict;

import java.util.List;

public interface ExternalApi {
    List<Problem> getAllProblems();

    /**
     * Submits solution.
     * <p>
     * If submission fails, does nothing.<br/>
     * If submission succeeds, sets {@link SubmissionResult#verdict submission.result.verdict} to {@link Verdict#PENDING}
     * and {@link Submission#externalSubmissionId submission.externalSubmissionId} to actual external submission id.<br/>
     *
     * @param submission Submission to send.
     */
    void submit(Submission submission);
    void submit(List<Submission> submissions);

    /**
     * Checks submission result.
     * <p>
     * If submission is not checked yet, sets {@link SubmissionResult#verdict submission.result.verdict} to {@link Verdict#PENDING}.<br/>
     * If submission is checked, updates {@link Submission#result submission.result} accordingly to the real result.
     *
     * @param submission Submission to check.
     */
    void checkSubmissionResult(Submission submission);
    void checkSubmissionResult(List<Submission> submissions);

    String getApiName();
}