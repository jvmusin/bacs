package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonProblem {
    private Integer id;
    private String name;
    private String statementUrl;
    private Integer collectionId;
    private Integer testsCount;
    private Integer pretestsCount;
    private String internalProblemId;
    private SybonResourceLimits resourceLimits;
}