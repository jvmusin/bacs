package istu.bacs.externalapi.sybonfake;

import istu.bacs.db.problem.Problem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static istu.bacs.externalapi.sybonfake.Measure.*;

public class ProblemService {

    private final List<Problem> problems = Arrays.asList(
            createProblem(1, "A + B", SECOND, GIGABYTE),
            createProblem(11, "A + B", SECOND, GIGABYTE),
            createProblem(12, "Дартс", 2 * SECOND, 256 * MEGABYTE),
            createProblem(13, "Карта", 2 * SECOND, 256 * MEGABYTE),
            createProblem(14, "difference", 2 * SECOND, 64 * MEGABYTE)
    );

    private static Problem createProblem(int problemId, String name, int timeLimitMillis, int memoryLimitBytes) {
        return Problem.builder()
                .problemId("SYBON@" + problemId)
                .problemName(name)
                .statementUrl("URL FOR " + problemId)
                .timeLimitMillis(timeLimitMillis)
                .memoryLimitBytes(memoryLimitBytes)
                .build();
    }

    public Problem findById(String id) {
        for (Problem problem : problems)
            if (Objects.equals(problem.getProblemId(), id))
                return problem;
        return null;
    }

    public List<Problem> getProblems() {
        return problems;
    }
}