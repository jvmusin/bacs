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
        ProblemDetails details = new ProblemDetails();
        details.setProblemName(sybonProblem.getName());
        details.setStatementUrl(sybonProblem.getStatementUrl());
        details.setPretestCount(sybonProblem.getPretestsCount());
        details.setTestCount(sybonProblem.getTestsCount());
        details.setTimeLimitMillis(sybonProblem.getResourceLimits().getTimeLimitMillis());
        details.setMemoryLimitBytes(sybonProblem.getResourceLimits().getMemoryLimitBytes());

        Problem problem = new Problem();
        problem.setProblemId(addResource(sybonProblem.getId(), SybonApi.API_NAME));
        problem.setDetails(details);

        return problem;
    }
}