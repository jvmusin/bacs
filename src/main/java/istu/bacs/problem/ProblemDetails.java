package istu.bacs.problem;

import lombok.Data;

@Data
public class ProblemDetails {
    private String problemName;
    private String statementUrl;

    private Integer pretestCount;
    private Integer testCount;

    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}