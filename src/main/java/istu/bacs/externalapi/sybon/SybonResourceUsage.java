package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
class SybonResourceUsage {
    private int timeUsageMillis;
    private int memoryUsageBytes;
}