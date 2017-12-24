package istu.bacs.contest;

import istu.bacs.problem.Problem;
import istu.bacs.standings.Standings;
import istu.bacs.standings.StandingsService;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import istu.bacs.user.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contests")
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
    public List<Contest> getContests() {
        return contestService.findAll();
    }

    @GetMapping("{contestId}")
    public Contest getContest(@PathVariable int contestId) {
        return contestService.findById(contestId);
    }

    @GetMapping("{contestId}/problems")
    public List<Problem> getProblems(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return contest.getProblems();
    }

    @GetMapping("{contestId}/problems/{problemIndex}")
    public Problem getProblem(@PathVariable int contestId, @PathVariable int problemIndex) {
        Contest contest = contestService.findById(contestId);
        return contest.getProblems().get(problemIndex);
    }

    @GetMapping("{contestId}/submissions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Submission> getAllSubmissions(@PathVariable int contestId) {
        return submissionService.findAllByContest(contestId);
    }

    @GetMapping("{contestId}/submissions/my")
    public List<Submission> getMySubmissions(@PathVariable int contestId, @AuthenticationPrincipal User author) {
        return submissionService.findAllByContestAndAuthor(contestId, author.getUserId());
    }

    @GetMapping("{contestId}/submissions/{submissionId}")
    public Submission getSubmission(@PathVariable int contestId, @PathVariable int submissionId) {
        return submissionService.findById(submissionId);
    }

    @PostMapping("{contestId}/problems/{problemIndex}")
    public void submit(@PathVariable int contestId, @PathVariable int problemIndex, @RequestBody SubmitSolutionDto submission, @AuthenticationPrincipal User author) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemIndex);

        Submission sub = new Submission();
        sub.setContest(contest);
        sub.setProblem(problem);
        sub.setAuthor(author);
        sub.setCreationTime(LocalDateTime.now());
        sub.setLanguage(submission.getLanguage());
        sub.setSolution(submission.getSolution());

        submissionService.submit(sub);
    }

    @GetMapping("{contestId}/standings")
    public Standings getStandings(@PathVariable int contestId) {
        //todo: Replace with DTO
        Contest contest = contestService.findById(contestId);
        return standingsService.getStandings(contest);
    }

    @PostMapping("editor")
    public void createContest(@ModelAttribute Contest contest) {
        //todo: Replace with DTO
        contestService.save(contest);
    }
}