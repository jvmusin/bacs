package istu.bacs.externalapi;

import istu.bacs.model.Problem;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ExternalApiProblemPreFetcher {

    private final ProblemService problemService;
    private final ExternalApiAggregator externalApi;

    public ExternalApiProblemPreFetcher(ProblemService problemService, ExternalApiAggregator externalApi) {
        this.problemService = problemService;
        this.externalApi = externalApi;
    }

    @PostConstruct
    public void fetchProblems() {
        List<Problem> problems = problemService.findAll().stream()
                .filter(p -> p.getDetails() == null)
                .distinct()
                .collect(toList());
        externalApi.updateProblemDetails(problems);
    }
}