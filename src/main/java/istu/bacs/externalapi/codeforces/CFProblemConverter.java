package istu.bacs.externalapi.codeforces;

import istu.bacs.externalapi.NumberHeadComparator;
import istu.bacs.model.Problem;
import istu.bacs.model.ProblemDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static istu.bacs.externalapi.ExternalApiHelper.addResource;

@Component
public class CFProblemConverter implements Converter<CFProblem, Problem> {

    private static final String STATEMENT_URL_FORMAT = "http://codeforces.com/contest/%d/problem/%s";

    @Override
    public Problem convert(CFProblem source) {
        Problem problem = new Problem();
        problem.setProblemId(addResource(source.getContestId() + source.getIndex(), CFApi.API_NAME));
        problem.setComparator(NumberHeadComparator.getInstance());

        ProblemDetails details = new ProblemDetails();
        details.setProblemName(source.getName());
        details.setStatementUrl(String.format(STATEMENT_URL_FORMAT, source.getContestId(), source.getIndex()));
        problem.setDetails(details);

        return problem;
    }
}
