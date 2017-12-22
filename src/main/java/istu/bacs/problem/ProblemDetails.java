package istu.bacs.problem;

import lombok.Data;

@Data
public class ProblemDetails {
    private String problemName;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;
}