package istu.bacs.externalapi.sybon;

import lombok.Data;

@Data
class SybonProblem {
    private Integer id;
    private String name;
    private String statementUrl;
    private Integer collectionId;
    private Integer testsCount;
    private Integer pretestsCount;
    private SybonResourceLimits resourceLimits;
}