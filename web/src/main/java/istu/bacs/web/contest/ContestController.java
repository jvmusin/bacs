package istu.bacs.web.contest;

import istu.bacs.standings.StandingsService;
import istu.bacs.web.model.get.Contest;
import istu.bacs.web.model.get.Problem;
import istu.bacs.web.model.get.Standings;
import istu.bacs.web.model.post.EditContest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public Contest getContest(@PathVariable int contestId) {
        return Contest.fromDb(contestService.findById(contestId));
    }

    @GetMapping("/{contestId}/problems")
    public List<Problem> getProblems(@PathVariable int contestId) {
        return contestService.findById(contestId).getProblems().stream()
                .map(Problem::fromDb)
                .collect(toList());
    }

    @GetMapping("{contestId}/problems/{problemIndex}")
    public Problem getProblem(@PathVariable int contestId, @PathVariable String problemIndex) {
        return Problem.fromDb(contestService.findById(contestId).getProblem(problemIndex));
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
    public void editContest(@RequestBody EditContest contest, @PathVariable int contestId) {
        contestService.editContest(contest, contestId);
    }
}