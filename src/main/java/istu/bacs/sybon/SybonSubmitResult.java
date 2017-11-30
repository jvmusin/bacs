package istu.bacs.sybon;

import lombok.Data;

@Data
class SybonSubmitResult {
    private Integer id;
    SybonBuildResult buildResult;
    SybonTestGroupResult[] testResults;
}