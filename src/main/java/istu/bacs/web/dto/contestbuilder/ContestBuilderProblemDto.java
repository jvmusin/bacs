package istu.bacs.web.dto.contestbuilder;

import istu.bacs.model.Problem;
import lombok.Data;

@Data
public class ContestBuilderProblemDto {
    private String id;
    private String name;
    private String statementUrl;

    public ContestBuilderProblemDto(Problem problem) {
        id = problem.getProblemId();
        name = problem.getDetails().getProblemName();
        statementUrl = problem.getDetails().getStatementUrl();
    }
}