package istu.bacs.externalapi.sybon;

import istu.bacs.model.Problem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(SybonProblem sybonProblem) {
        return new Problem(
                "SYBON@" + sybonProblem.getId(),
                new Problem.ProblemDetails(
                sybonProblem.getName(),
                sybonProblem.getStatementUrl(),
                sybonProblem.getPretestsCount(),
                sybonProblem.getTestsCount(),
                sybonProblem.getResourceLimits().getTimeLimitMillis(),
                sybonProblem.getResourceLimits().getMemoryLimitBytes())
        );
    }
}