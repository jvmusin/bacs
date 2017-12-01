package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResult {
    private String status;
    private String judgeMessage;
    private String input;
    private String output;
    private String expected;

    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}
