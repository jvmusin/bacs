package istu.bacs.externalapi.sybon;

import lombok.Data;

import java.util.List;

@Data
class SybonTestGroupResult {
    private Boolean executed;
    private List<SybonTestResult> testResults;
}