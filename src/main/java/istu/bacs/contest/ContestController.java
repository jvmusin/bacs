package istu.bacs.contest;

import istu.bacs.problem.Problem;
import istu.bacs.standings.Standings;
import istu.bacs.standings.StandingsService;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import istu.bacs.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContestController {

    private final ContestService contestService;
    private final SubmissionService submissionService;
    private final StandingsService standingsService;

    public ContestController(ContestService contestService, SubmissionService submissionService, StandingsService standingsService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.standingsService = standingsService;
    }

    @GetMapping("/contests")
    public List<Contest> getContests() {
        return contestService.findAll();
    }

    @GetMapping("/contest/{contestId}")
    public List<Problem> getContestProblems(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return contest.getProblems();
    }

    @GetMapping("/contest/{contestId}/problem/{problemLetter}")
    public String getStatementUrl(@PathVariable int contestId, @PathVariable char problemLetter) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemLetter - 'A');
        return problem.getDetails().getStatementUrl();
    }

    @GetMapping("/contest/{contestId}/submissions")
    public List<Submission> getContestSubmissions(@PathVariable int contestId, @AuthenticationPrincipal User author) {
        return submissionService.findAllByContestAndAuthor(contestId, author.getUserId());
    }

    @PostMapping("/contest/{contestId}/submit")
    public void submit(@ModelAttribute Submission submission,
                       @PathVariable int contestId) {
        submissionService.submit(submission);
    }

    @GetMapping("/contest/{contestId}/submission/{submissionId}")
    public Submission getSubmission(@PathVariable int contestId,
                                    @PathVariable int submissionId) {
        return submissionService.findById(submissionId);
    }

    @GetMapping("/contest/{contestId}/standings")
    public Standings getStandings(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return standingsService.getStandings(contest);
    }

    @PostMapping("/contests/build")
    public void buildContest(@ModelAttribute Contest contest) {
        contestService.save(contest);
    }
}