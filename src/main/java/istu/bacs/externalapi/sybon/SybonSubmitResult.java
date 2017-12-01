package istu.bacs.externalapi.sybon;

import lombok.Data;

import java.util.List;

@Data
class SybonSubmitResult {
    private Integer id;
    private SybonBuildResult buildResult;
    private List<SybonTestGroupResult> testResults;
}