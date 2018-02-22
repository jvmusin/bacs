package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ResourceName;
import istu.bacs.web.model.problem.ArchiveProblem;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/problems")
@AllArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public List<ArchiveProblem> getAllProblems(
            @RequestParam(name = "external", required = false) String external,

            @RequestParam(name = "contestId", required = false) Integer contestId,
            @RequestParam(name = "problemIndex", required = false) String problemIndex,

            @RequestParam(name = "resourceName", required = false) ResourceName resourceName,
            @RequestParam(name = "resourceProblemId", required = false) String resourceProblemId,

            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "50") int pageSize) {

        if (external != null) {
            problemService.fetchExternalProblems();
        }

        if (contestId != null && problemIndex != null) {
            Problem problem = problemService.findByContestIdAndProblemIndex(contestId, problemIndex);
            ArchiveProblem archiveProblem = ArchiveProblem.fromDb(problem);
            return singletonList(archiveProblem);
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return problemService.findAll(resourceName, resourceProblemId, pageable).stream()
                .map(ArchiveProblem::fromDb)
                .collect(toList());
    }
}