package istu.bacs.submission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResult {
    private Verdict verdict;
    private String judgeMessage;
    private String input;
    private String output;
    private String expected;

    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}
