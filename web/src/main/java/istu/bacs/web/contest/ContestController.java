package istu.bacs.web.contest;

import istu.bacs.standings.StandingsService;
import istu.bacs.web.model.contest.Contest;
import istu.bacs.web.model.contest.builder.EditContest;
import istu.bacs.web.model.contest.standings.Standings;
import istu.bacs.web.model.problem.ContestProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/contests")
@AllArgsConstructor
@Slf4j
public class ContestController {

    private final ContestService contestService;
    private final StandingsService standingsService;

    @GetMapping
    public List<Contest> getAllContests(
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "50") int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("contestId").descending());

        return contestService.findAll(pageable).stream()
                .map(Contest::fromDb)
                .collect(toList());
    }

    @GetMapping("/{contestId}")
    public ResponseEntity<Contest> getContest(@PathVariable int contestId) {
        istu.bacs.db.contest.Contest contest = contestService.findById(contestId);
        if (contest == null) return notFound().build();
        return ok().body(Contest.fromDb(contest));
    }

    @GetMapping("/{contestId}/problems")
    public List<ContestProblem> getProblems(@PathVariable int contestId) {
        return contestService.findById(contestId).getProblems().stream()
                .map(ContestProblem::fromDb)
                .collect(toList());
    }

    @GetMapping("{contestId}/problems/{problemIndex}")
    public ContestProblem getProblem(@PathVariable int contestId, @PathVariable String problemIndex) {
        return ContestProblem.fromDb(contestService.findById(contestId).getProblem(problemIndex));
    }

    @GetMapping("/{contestId}/standings")
    public Standings getStandings(@PathVariable int contestId) {
        return standingsService.getStandings(contestId);
    }

    @PostMapping
    public int createContest(@RequestBody EditContest contest) {
        return contestService.createContest(contest);
    }

    @PutMapping("/{contestId}")
    public ResponseEntity<?> editContest(@RequestBody EditContest contest, @PathVariable int contestId) {
        try {
            contestService.editContest(contest, contestId);
            return ok().build();
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{contestId}")
    public void deleteContest(@PathVariable int contestId) {
        contestService.deleteContest(contestId);
    }
}