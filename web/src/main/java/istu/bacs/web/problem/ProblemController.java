package istu.bacs.web.problem;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.model.get.Problem;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/problems")
@AllArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final ExternalApi externalApi;

    @GetMapping
    public List<Problem> getAllProblems(@RequestParam(name = "external", required = false) String external) {
        if (external != null) {
            problemService.saveAll(externalApi.getAllProblems());
        }
        return problemService.findAllProblems().stream()
                .map(Problem::fromDb)
                .collect(toList());
    }

    @GetMapping("/{problemId}")
    public Problem getProblem(@PathVariable String problemId) {
        return Problem.fromDb(problemService.findById(problemId));
    }
}