package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceUsage {
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}
