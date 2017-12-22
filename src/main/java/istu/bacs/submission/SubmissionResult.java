package istu.bacs.submission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionResult {
    private boolean built;
    private String buildInfo;

    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}