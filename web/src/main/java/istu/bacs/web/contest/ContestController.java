package istu.bacs.web.contest;

import istu.bacs.db.user.User;
import istu.bacs.db.util.OffsetBasedPageRequest;
import istu.bacs.standingsapi.StandingsService;
import istu.bacs.standingsapi.dto.StandingsDto;
import istu.bacs.web.contest.dto.ContestMetaDto;
import istu.bacs.web.contest.dto.FullContestDto;
import istu.bacs.web.problem.ProblemService;
import istu.bacs.web.problem.dto.ProblemDto;
import istu.bacs.web.problem.dto.SubmitSolutionDto;
import istu.bacs.web.submission.SubmissionService;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;
import istu.bacs.web.submission.dto.SubmissionMetaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("contests")
@Slf4j
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

        Pageable pageable = OffsetBasedPageRequest.fromNullableOffsetAndLimit(offset, limit, Sort.by("contestId").descending());

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
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("submissionId").descending());
        return submissionService.findAllByContest(contestId, pageable).stream()
                .map(SubmissionMetaDto::new)
                .collect(toList());
    }

    @GetMapping("{contestId}/submissions/my")
    public List<SubmissionMetaDto> getMySubmissions(
            @PathVariable int contestId,
            @AuthenticationPrincipal User author,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex) {

        if ((pageSize == null) != (pageIndex == null))
            throw new IllegalArgumentException("pageSize and pageIndex should be both null or not null");

        if (pageSize == null) {
            pageSize = Integer.MAX_VALUE;
            pageIndex = 0;
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("submissionId").descending());

        return submissionService.findAllByContestAndAuthor(contestId, author, pageable).stream()
                .map(SubmissionMetaDto::new)
                .collect(toList());
    }

    @PostMapping("{contestId}/problems/{problemIndex}")
    public int submit(@PathVariable int contestId,
                      @PathVariable String problemIndex,
                      @RequestBody SubmitSolutionDto submission,
                      @AuthenticationPrincipal User author) {

        log.debug("User {}:'{}' submitted a solution for problem {} in contest {} using {}",
                author.getUserId(),
                author.getUsername(),
                problemIndex,
                contestId,
                submission.getLanguage());

        return submissionService.submit(
                new EnhancedSubmitSolutionDto(contestId, problemIndex, author, submission)
        );
    }

    @GetMapping("{contestId}/standings")
    public StandingsDto getStandings(@PathVariable int contestId) {
        return standingsService.getStandings(contestId);
    }
}