package istu.bacs.externalapi.sybon;

import istu.bacs.problem.Problem;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static istu.bacs.externalapi.ExternalApiHelper.addResource;

@Component
public class SybonProblemConverter implements Converter<SybonProblem, Problem> {
    @Override
    public Problem convert(@NotNull SybonProblem sybonProblem) {
        return Problem.builder()
                .problemId(addResource(sybonProblem.getId(), SybonApi.API_NAME))
                .problemName(sybonProblem.getName())
                .statementUrl(sybonProblem.getStatementUrl())
                .timeLimitMillis(sybonProblem.getResourceLimits().getTimeLimitMillis())
                .memoryLimitBytes(sybonProblem.getResourceLimits().getMemoryLimitBytes())
                .build();
    }
}