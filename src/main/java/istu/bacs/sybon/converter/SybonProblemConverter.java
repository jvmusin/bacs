package istu.bacs.sybon.converter;

import istu.bacs.model.Problem;
import istu.bacs.sybon.SybonProblem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(SybonProblem sybonProblem) {
        return new Problem(
                sybonProblem.getId(),
                sybonProblem.getName(),
                sybonProblem.getStatementUrl(),
                sybonProblem.getPretestsCount(),
                sybonProblem.getTestsCount(),
                sybonProblem.getResourceLimits().getTimeLimitMillis(),
                sybonProblem.getResourceLimits().getMemoryLimitBytes()
        );
    }
}