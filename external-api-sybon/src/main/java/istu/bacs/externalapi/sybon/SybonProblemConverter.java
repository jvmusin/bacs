package istu.bacs.externalapi.sybon;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemId;
import org.springframework.core.convert.converter.Converter;

import static istu.bacs.db.problem.ResourceName.SYBON;

public class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(SybonProblem sybonProblem) {
        return Problem.builder()
                .problemId(new ProblemId(SYBON, sybonProblem.getId() + ""))
                .name(sybonProblem.getName())
                .statementUrl(sybonProblem.getStatementUrl())
                .timeLimitMillis(sybonProblem.getResourceLimits().getTimeLimitMillis())
                .memoryLimitBytes(sybonProblem.getResourceLimits().getMemoryLimitBytes())
                .build();
    }
}