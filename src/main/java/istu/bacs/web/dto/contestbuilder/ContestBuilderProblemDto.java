package istu.bacs.web.dto.contestbuilder;

import istu.bacs.model.Problem;
import istu.bacs.model.ProblemDetails;
import lombok.Data;

@Data
public class ContestBuilderProblemDto {
    private String id;
    private String name;
    private String statementUrl;

    public ContestBuilderProblemDto(Problem problem) {
        ProblemDetails details = problem.getDetails();
        id = problem.getProblemId();
        name = details.getProblemName();
        statementUrl = details.getStatementUrl();
    }
}