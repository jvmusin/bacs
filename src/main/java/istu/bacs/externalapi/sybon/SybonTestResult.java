package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
public class SybonTestResult {
    private SybonTestResultStatus status;
    private String judgeMessage;
    private SybonResourceUsage resourceUsage;
    private String input;
    private String actualResult;
    private String expectedResult;
}