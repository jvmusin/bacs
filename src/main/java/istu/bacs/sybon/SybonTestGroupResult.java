package istu.bacs.sybon;

import lombok.Data;

@Data
class SybonTestGroupResult {
    private Boolean executed;
    private SybonTestResult[] testResults;
}