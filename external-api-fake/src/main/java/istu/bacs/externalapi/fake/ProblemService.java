package istu.bacs.externalapi.fake;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemId;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static istu.bacs.db.problem.ResourceName.FAKE;
import static istu.bacs.externalapi.fake.Measure.*;

class ProblemService {

    @SuppressWarnings("PointlessArithmeticExpression")
    @Getter
    private final List<Problem> problems = Arrays.asList(
            createProblem(1,  "Hello!",           1 * SECOND,        1 * GIGABYTE),
            createProblem(2,  "How",              3 * SECOND,        1 * GIGABYTE),
            createProblem(5,  "To",            1500 * MILLISECOND, 512 * MEGABYTE),
            createProblem(10, "Meet",           500 * MILLISECOND, 256 * MEGABYTE),
            createProblem(11, "A Girlfriend?",    1 * SECOND,       64 * MEGABYTE)
    );

    private static Problem createProblem(int problemId, String name, int timeLimitMillis, int memoryLimitBytes) {
        return Problem.builder()
                .problemId(new ProblemId(FAKE, problemId + ""))
                .name(name)
                .statementUrl("URL_FOR_FAKED_PROBLEM_WITH_ID_" + problemId)
                .timeLimitMillis(timeLimitMillis)
                .memoryLimitBytes(memoryLimitBytes)
                .build();
    }
}