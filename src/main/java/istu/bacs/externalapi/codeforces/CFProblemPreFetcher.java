package istu.bacs.externalapi.codeforces;

import istu.bacs.model.Problem;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CFProblemPreFetcher {

    private final CFApi cfApi;
    private final ProblemService problemService;

    public CFProblemPreFetcher(CFApi cfApi, ProblemService problemService) {
        this.cfApi = cfApi;
        this.problemService = problemService;
    }

    @PostConstruct
    public void fetchProblems() {
        List<Problem> problems = cfApi.getAllProblems();
        for (Problem problem : problems)
            System.out.println(problem);
        problemService.saveAll(problems);
    }
}