package istu.bacs.externalapi.sybon;

import istu.bacs.model.Problem;
import istu.bacs.model.ProblemDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static istu.bacs.externalapi.ExternalApiHelper.addResource;

@Component
class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(SybonProblem sybonProblem) {
        return new Problem(
                addResource(sybonProblem.getId(), SybonApiImpl.API_NAME),
                new ProblemDetails(
                        sybonProblem.getName(),
                        sybonProblem.getStatementUrl(),
                        sybonProblem.getPretestsCount(),
                        sybonProblem.getTestsCount(),
                        sybonProblem.getResourceLimits().getTimeLimitMillis(),
                        sybonProblem.getResourceLimits().getMemoryLimitBytes()
                )
        );
    }
}