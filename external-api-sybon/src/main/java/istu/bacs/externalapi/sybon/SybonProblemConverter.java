package istu.bacs.externalapi.sybon;

import istu.bacs.db.problem.Problem;
import org.springframework.core.convert.converter.Converter;

import static istu.bacs.externalapi.sybon.SybonApi.API_NAME;

public class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(SybonProblem sybonProblem) {
        return Problem.builder()
                .problemId(API_NAME + "@" + sybonProblem.getId())
                .name(sybonProblem.getName())
                .statementUrl(sybonProblem.getStatementUrl())
                .timeLimitMillis(sybonProblem.getResourceLimits().getTimeLimitMillis())
                .memoryLimitBytes(sybonProblem.getResourceLimits().getMemoryLimitBytes())
                .build();
    }
}