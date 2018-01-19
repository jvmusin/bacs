package istu.bacs.externalapi;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;

import java.util.List;

public interface ExternalApi {
    List<Problem> getAllProblems();

    /**
     * Submits solution.
     * <p>
     * If submission fails, sets externalSubmissionId to null and submissionResult to NOT_SUBMITTED.<br/>
     * If submission succeeded, sets externalSubmissionId to real externalSubmissionId and submissionResult to PENDING.
     *
     * @param submission Submission to send.
     */
    void submit(Submission submission);
    void submit(List<Submission> submissions);

    /**
     * Updates submission details.
     * <p>
     * If submission is not checked yet, does nothing.<br/>
     * If submission is checked, updates its submissionResult accordingly to the real result.
     *
     * @param submission Submission to check.
     */
    void updateSubmissionDetails(Submission submission);
    void updateSubmissionDetails(List<Submission> submissions);

    String getApiName();
}