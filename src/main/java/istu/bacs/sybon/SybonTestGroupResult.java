package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonTestGroupResult {
    private Integer internalId;
    private Boolean executed;
    private SybonTestResult[] testResults;
}