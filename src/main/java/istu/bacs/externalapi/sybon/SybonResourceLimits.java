package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
public class SybonResourceLimits {
    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}