package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Verdict;
import lombok.Value;

@Value
public class SubmissionResult {
    String buildInfo;
    Verdict verdict;
    Integer testsPassed;
    Integer timeUsed;
    Integer memoryUsed;

    public static SubmissionResult fromDb(istu.bacs.db.submission.SubmissionResult result) {
        return new SubmissionResult(
                result.getBuildInfo(),
                result.getVerdict(),
                result.getTestsPassed(),
                result.getTimeUsedMillis(),
                result.getMemoryUsedBytes()
        );
    }
}