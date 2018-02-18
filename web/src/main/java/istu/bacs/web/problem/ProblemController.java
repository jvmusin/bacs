package istu.bacs.web.problem;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.model.problem.ArchiveProblem;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<ArchiveProblem> getAllProblems(
            @RequestParam(name = "external", required = false) String external,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "50") int pageSize) {

        if (external != null)
            problemService.saveAll(externalApi.getAllProblems());

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("contestId").descending());

        return problemService.findAll(pageable).stream()
                .map(ArchiveProblem::fromDb)
                .collect(toList());
    }

    @GetMapping("/{problemId}")
    public ArchiveProblem getProblem(@PathVariable String problemId) {
        return ArchiveProblem.fromDb(problemService.findById(problemId));
    }
}