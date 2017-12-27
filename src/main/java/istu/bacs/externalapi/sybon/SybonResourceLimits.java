package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
public class SybonResourceLimits {
    private int timeLimitMillis;
    private int memoryLimitBytes;
}