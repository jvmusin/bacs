package istu.bacs.sybon;

import lombok.*;

@Data
public class SybonResourceLimits {
    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}