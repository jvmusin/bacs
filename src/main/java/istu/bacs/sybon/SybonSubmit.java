package istu.bacs.sybon;

import lombok.Data;

@Data
class SybonSubmit {
    private Integer compilerId;
    private String solution;
    private String solutionFileType;
    private Integer problemId;
    private Integer userId;
    private Boolean pretestsOnly;
}