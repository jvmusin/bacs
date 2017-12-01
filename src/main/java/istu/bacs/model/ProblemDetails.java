package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDetails {
    private String problemName;
    private String statementUrl;

    private Integer pretestCount;
    private Integer testCount;

    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}