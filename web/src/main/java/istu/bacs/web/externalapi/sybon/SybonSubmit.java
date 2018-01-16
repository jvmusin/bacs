package istu.bacs.web.externalapi.sybon;

import lombok.Data;

@Data
public class SybonSubmit {
    private int compilerId;
    private String solution;
    private String solutionFileType;
    private int problemId;
    private boolean pretestsOnly;
    private SybonContinueCondition continueCondition;
}