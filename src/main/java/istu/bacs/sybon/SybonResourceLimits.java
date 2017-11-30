package istu.bacs.sybon;

import lombok.Data;

@Data
class SybonResourceLimits {
    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}