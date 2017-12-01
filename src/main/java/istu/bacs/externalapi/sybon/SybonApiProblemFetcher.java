package istu.bacs.externalapi.sybon;

import istu.bacs.service.ContestService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SybonApiProblemFetcher {

    private final ContestService contestService;
    private final SybonApiImpl sybonApi;

    public SybonApiProblemFetcher(ContestService contestService, SybonApiImpl sybonApi) {
        this.contestService = contestService;
        this.sybonApi = sybonApi;
    }

    @PostConstruct
    public void fetchProblems() {
        contestService.findAll().forEach(c -> sybonApi.updateProblemDetails(c.getProblems()));
    }
}