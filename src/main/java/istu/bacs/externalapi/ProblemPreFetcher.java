package istu.bacs.externalapi;

import istu.bacs.model.Problem;
import istu.bacs.service.ContestService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProblemPreFetcher {

    private final ContestService contestService;
    private final ExternalApiAggregator externalApi;

    public ProblemPreFetcher(ContestService contestService, ExternalApiAggregator externalApi) {
        this.contestService = contestService;
        this.externalApi = externalApi;
    }

    @PostConstruct
    public void fetchProblems() {
        List<Problem> problems = contestService.findAll().stream()
                .flatMap(c -> c.getProblems().stream())
                .distinct()
                .collect(Collectors.toList());
        externalApi.updateProblemDetails(problems);
    }
}