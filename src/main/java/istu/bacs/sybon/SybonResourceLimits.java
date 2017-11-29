package istu.bacs.sybon;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
public class SybonResourceLimits {
    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
}