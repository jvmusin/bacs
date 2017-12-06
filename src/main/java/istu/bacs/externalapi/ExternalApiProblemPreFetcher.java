package istu.bacs.externalapi;

import istu.bacs.model.Problem;
import istu.bacs.service.ContestService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ExternalApiProblemPreFetcher {

    private final ContestService contestService;
    private final ExternalApiAggregator externalApi;

    public ExternalApiProblemPreFetcher(ContestService contestService, ExternalApiAggregator externalApi) {
        this.contestService = contestService;
        this.externalApi = externalApi;
    }

    @PostConstruct
    public void fetchProblems() {
        List<Problem> problems = contestService.findAll().stream()
                .flatMap(c -> c.getProblems().stream())
                .filter(p -> p.getDetails() == null)
                .distinct()
                .collect(toList());
        externalApi.updateProblems(problems);
    }
}