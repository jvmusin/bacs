package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonSubmitResult {
    private Integer id;
    SybonBuildResult buildResult;
    SybonTestGroupResult[] testResults;
}