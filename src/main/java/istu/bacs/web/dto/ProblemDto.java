package istu.bacs.web.dto;

import istu.bacs.model.Problem;
import istu.bacs.model.ProblemDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
public class ProblemDto {

    private static final String PROBLEM_NAME_FORMAT = "%c. %s";
    private static final String STATEMENT_URL_FORMAT = "/contest/%d/problem/%c";

    private int contestId;
    private int index;
    private String name;
    private Integer timeLimitSeconds;
    private Integer memoryLimitMegabytes;
    private String statementUrl;

    public ProblemDto(int contestId, int index, Problem problem) {
        this.contestId = contestId;
        this.index = index;

        ProblemDetails details = problem.getDetails();
        this.name = details.getProblemName();

        if (details.getTimeLimitMillis() != null) this.timeLimitSeconds = details.getTimeLimitMillis() / 1000;
        if (details.getMemoryLimitBytes() != null) this.memoryLimitMegabytes = details.getMemoryLimitBytes() / 1024 / 1024;
    }

    public String getIndexedName() {
        return String.format(PROBLEM_NAME_FORMAT, index + 'A', name);
    }

    public String getStatementUrl() {
        return String.format(STATEMENT_URL_FORMAT, contestId, index + 'A');
    }

    public static List<ProblemDto> convert(List<Problem> problems, int contestId) {
        int problemCount = problems.size();
        List<ProblemDto> result = new ArrayList<>(problemCount);
        for (int i = 0; i < problemCount; i++)
            result.add(new ProblemDto(contestId, i, problems.get(i)));
        return result;
    }
}