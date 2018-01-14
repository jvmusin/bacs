package istu.bacs.contest;

import istu.bacs.contest.dto.ContestMetaDto;
import istu.bacs.contest.dto.FullContestDto;
import istu.bacs.contest.dto.SubmitSolutionDto;
import istu.bacs.problem.dto.ProblemDto;
import istu.bacs.standings.Standings;
import istu.bacs.standings.StandingsService;
import istu.bacs.standings.dto.StandingsDto;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import istu.bacs.submission.dto.SubmissionMetaDto;
import istu.bacs.user.User;
import istu.bacs.util.OffsetBasedPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static istu.bacs.submission.SubmissionResult.withVerdict;
import static istu.bacs.submission.Verdict.NOT_SUBMITTED;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("contests")
public class ContestController {

    private final ContestService contestService;
    private final SubmissionService submissionService;
    private final StandingsService standingsService;

    public ContestController(ContestService contestService, SubmissionService submissionService, StandingsService standingsService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.standingsService = standingsService;
    }

    @GetMapping
    public List<ContestMetaDto> getContests(
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit) {

        if (offset == null) offset = 0;
        if (limit == null) limit = 500;
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, new Sort(Sort.Direction.DESC, "contestId"));

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
        Contest contest = Contest.builder().contestId(contestId).build();
        return new ProblemDto(contestService.findProblem(contest, problemIndex));
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

        //todo: Method shouldn't have any logic
        Contest contest = Contest.builder().contestId(contestId).build();
        ContestProblem problem = contestService.findProblem(contest, problemIndex);

        Submission sub = new Submission();
        sub.setContestProblem(problem);
        sub.setAuthor(author);
        sub.setCreated(LocalDateTime.now());
        sub.setLanguage(submission.getLanguage());
        sub.setSolution(submission.getSolution());
        sub.setResult(withVerdict(sub, NOT_SUBMITTED));

        return submissionService.submit(sub);
    }

    @GetMapping("{contestId}/standings")
    public StandingsDto getStandings(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        Standings standings = standingsService.getStandings(contest);
        return new StandingsDto(standings);
    }
}