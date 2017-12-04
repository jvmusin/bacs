package istu.bacs.web.dto;

import istu.bacs.model.Problem;
import istu.bacs.model.ProblemDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ProblemDto {

    private static final String PROBLEM_NAME_FORMAT = "%c. %s";
    private static final String STATEMENT_URL_FORMAT = "/contest/%d/problem/%c";

    private int contestId;
    private int index;
    private String name;
    private int timeLimitSeconds;
    private int memoryLimitMegabytes;
    private String statementUrl;

    public ProblemDto(int contestId, int index, Problem problem) {
        this.contestId = contestId;
        this.index = index;

        ProblemDetails details = problem.getDetails();
        this.name = problem.getDetails().getProblemName();
        this.timeLimitSeconds = details.getTimeLimitMillis() / 1000;
        this.memoryLimitMegabytes = details.getMemoryLimitBytes() / 1024 / 1024;
    }

    public String getIndexedName() {
        return String.format(PROBLEM_NAME_FORMAT, index + 'A', name);
    }

    public String getStatementUrl() {
        return String.format(STATEMENT_URL_FORMAT, contestId, index + 'A');
    }
}