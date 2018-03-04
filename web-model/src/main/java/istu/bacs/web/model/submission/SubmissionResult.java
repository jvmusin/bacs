package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResult {
    private String buildInfo;
    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsed;
    private Integer memoryUsed;

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