package istu.bacs.contest;

import istu.bacs.contest.dto.ContestMetaDto;
import istu.bacs.contest.dto.FullContestDto;
import istu.bacs.problem.ProblemService;
import istu.bacs.problem.dto.ProblemDto;
import istu.bacs.problem.dto.SubmitSolutionDto;
import istu.bacs.standings.Standings;
import istu.bacs.standings.StandingsService;
import istu.bacs.standings.dto.StandingsDto;
import istu.bacs.submission.SubmissionService;
import istu.bacs.submission.dto.EnhancedSubmitSolutionDto;
import istu.bacs.submission.dto.SubmissionMetaDto;
import istu.bacs.user.User;
import istu.bacs.util.OffsetBasedPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("contests")
public class ContestController {

    private final ContestService contestService;
    private final ProblemService problemService;
    private final SubmissionService submissionService;
    private final StandingsService standingsService;

    public ContestController(ContestService contestService, ProblemService problemService, SubmissionService submissionService, StandingsService standingsService) {
        this.contestService = contestService;
        this.problemService = problemService;
        this.submissionService = submissionService;
        this.standingsService = standingsService;
    }

    @GetMapping
    public List<ContestMetaDto> getContests(
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit) {

        if (offset == null) offset = 0;
        if (limit == null) limit = 500;
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, new Sort(DESC, "contestId"));

        return contestService.findAll(pageable).stream()
                .map(ContestMetaDto::new)
                .collect(toList());
    }

    @GetMapping("{contestId}")
    public FullContestDto getContest(@PathVariable int contestId) {
        return new FullContestDto(contestService.findById(contestId));
    }

    @GetMapping("{contestId}/problems/{problemIndex}")
    public ProblemDto getProblem(@PathVariable int contestId, @PathVariable String problemIndex) {
        return new ProblemDto(problemService.findByContestAndProblemIndex(contestId, problemIndex));
    }

    @GetMapping("{contestId}/submissions")
    public List<SubmissionMetaDto> getAllSubmissions(@PathVariable int contestId) {
        return submissionService.findAllByContest(contestId).stream()
                .map(SubmissionMetaDto::new)
                .collect(toList());
    }

    @GetMapping("{contestId}/submissions/my")
    public List<SubmissionMetaDto> getMySubmissions(@PathVariable int contestId, @AuthenticationPrincipal User author) {
        return submissionService.findAllByContestAndAuthor(contestId, author.getUserId()).stream()
                .map(SubmissionMetaDto::new)
                .collect(toList());
    }

    @PostMapping("{contestId}/problems/{problemIndex}")
    public int submit(@PathVariable int contestId,
                      @PathVariable String problemIndex,
                      @RequestBody SubmitSolutionDto submission,
                      @AuthenticationPrincipal User author) {

        return submissionService.submit(new EnhancedSubmitSolutionDto(contestId, problemIndex, author, submission));
    }

    @GetMapping("{contestId}/standings")
    public StandingsDto getStandings(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        Standings standings = standingsService.getStandings(contest);
        return new StandingsDto(standings);
    }
}