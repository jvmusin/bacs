package istu.bacs.web.problem;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.problem.dto.ProblemDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("problems")
public class ProblemController {

    private final ProblemService problemService;
    private final ExternalApi externalApi;

    public ProblemController(ProblemService problemService, ExternalApi externalApi) {
        this.problemService = problemService;
        this.externalApi = externalApi;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<ProblemDto> getProblems() {
        return problemService.findAll().stream()
                .map(problem -> {
                    ProblemDto p = new ProblemDto(problem);
                    p.setIndex(problem.getProblemId());
                    return p;
                })
                .collect(toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("external")
    public List<ProblemDto> fetchExternal() {
        problemService.saveAll(externalApi.getAllProblems());
        return getProblems();
    }
}