package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonTestResult {
    private Status status;
    private String judgeMessage;
    private SybonResourceUsage resourceUsage;
    private String input;
    private String actualResult;
    private String expectedResult;

    public enum Status {
        OK,
        WRONG_ANSWER,
        PRESENTATION_ERROR,
        QUERIES_LIMIT_EXCEEDED,
        INCORRECT_REQUEST,
        INSUFFICIENT_DATA,
        EXCESS_DATA,
        OUTPUT_LIMIT_EXCEEDED,
        TERMINATION_REAL_TIME_LIMIT_EXCEEDED,
        ABNORMAL_EXIT,
        MEMORY_LIMIT_EXCEEDED,
        TIME_LIMIT_EXCEEDED,
        REAL_TIME_LIMIT_EXCEEDED,
        TERMINATED_BY_SYSTEM,
        CUSTOM_FAILURE,
        FAIL_TEST,
        FAILED,
        SKIPPED
    }
}