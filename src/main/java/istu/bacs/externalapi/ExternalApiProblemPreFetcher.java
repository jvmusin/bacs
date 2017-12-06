package istu.bacs.externalapi;

import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
        externalApi.updateProblemDetails(problemService.findAll());
    }
}