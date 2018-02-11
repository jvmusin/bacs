package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.contest.ContestService;
import istu.bacs.web.model.problem.ArchiveProblem;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/problems")
@AllArgsConstructor
public class ProblemController {

    private final ContestService contestService;
    private final ProblemService problemService;
    private final ExternalApi externalApi;

    @GetMapping
    public List<ArchiveProblem> getAllProblems(@RequestParam(name = "external", required = false) String external,
                                               @RequestParam(name = "contestId", required = false) Integer contestId,
                                               @RequestParam(name = "problemIndex", required = false) String problemIndex) {
        if (external != null) {
            problemService.saveAll(externalApi.getAllProblems());
        }

        if (contestId != null && problemIndex != null) {
            Problem problem = contestService.findById(contestId).getProblem(problemIndex).getProblem();
            return Collections.singletonList(ArchiveProblem.fromDb(problem));
        }

        return problemService.findAllProblems().stream()
                .map(ArchiveProblem::fromDb)
                .collect(toList());
    }

    @GetMapping("/{problemId}")
    public ArchiveProblem getProblem(@PathVariable String problemId) {
        return ArchiveProblem.fromDb(problemService.findById(problemId));
    }
}