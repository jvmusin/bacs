package istu.bacs.web.externalapi.sybon;

import lombok.Data;

@Data
public class SybonProblem {
    private int id;
    private String name;
    private String statementUrl;
    private int collectionId;
    private int testsCount;
    private SybonResourceLimits resourceLimits;
}