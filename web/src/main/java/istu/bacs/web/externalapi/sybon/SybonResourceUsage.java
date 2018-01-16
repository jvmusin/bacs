package istu.bacs.web.externalapi.sybon;

import lombok.Data;

@Data
public class SybonResourceUsage {
    private int timeUsageMillis;
    private int memoryUsageBytes;
}