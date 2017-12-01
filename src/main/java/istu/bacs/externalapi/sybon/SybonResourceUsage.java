package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
class SybonResourceUsage {
    private Integer timeUsageMillis;
    private Integer memoryUsageBytes;
}