package istu.bacs.contest;

import istu.bacs.problem.Problem;
import istu.bacs.standings.StandingsDto;
import istu.bacs.standings.StandingsService;
import istu.bacs.standings.Standings;
import istu.bacs.submission.*;
import istu.bacs.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Controller
public class ContestController {

    private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
    private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
    private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
    private static final String VIEWS_SUBMISSION_VIEW = "contests/submissions/submission-view";
    private static final String VIEWS_SUBMIT_PAGE = "contests/submissions/submit-solution";
    private static final String VIEWS_CONTEST_STANDINGS = "contests/contest-standings";

    private final ContestService contestService;
    private final SubmissionService submissionService;
    private final StandingsService standingsService;

    public ContestController(ContestService contestService, SubmissionService submissionService, StandingsService standingsService) {
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.standingsService = standingsService;
    }

    @GetMapping("/contests")
    public ModelAndView loadAllContests() {
        List<Contest> contests = contestService.findAll();
        return new ModelAndView(VIEWS_CONTEST_LIST, "model", new ContestListDto(contests));
    }

    @GetMapping("/contest/{contestId}")
    public ModelAndView loadContestProblems(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return new ModelAndView(VIEWS_CONTEST_PROBLEMS, "contest", ContestDto.withProblems(contest));
    }

    @GetMapping("/contest/{contestId}/problem/{problemLetter}")
    public RedirectView loadStatement(@PathVariable int contestId, @PathVariable char problemLetter) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemLetter - 'A');
        String statementUrl = problem.getDetails().getStatementUrl();
        return new RedirectView(statementUrl);
    }

    @GetMapping("/contest/{contestId}/submissions")
    public ModelAndView loadContestSubmissions(@PathVariable int contestId, @AuthenticationPrincipal User author) {
        Contest contest = contestService.findById(contestId);
        List<Submission> submissions = submissionService.findAllByContestAndAuthor(contestId, author.getUserId());
        return new ModelAndView(VIEWS_SUBMISSION_LIST, "model",
                new ContestSubmissionsDto(contest.getContestName(), submissions));
    }

    @GetMapping("/contest/{contestId}/submit")
    public ModelAndView loadSubmissionForm(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);

        return new ModelAndView(VIEWS_SUBMIT_PAGE, "model",
                new SubmissionFormDto(contestId, EnumSet.allOf(Language.class), contest.getProblems()));
    }

    @PostMapping("/contest/{contestId}/submit")
    public RedirectView submit(@ModelAttribute SubmissionDto submission,
                               @PathVariable int contestId,
                               @RequestParam MultipartFile file,
                               @AuthenticationPrincipal User user) throws IOException {
        //todo: add user checking to controller
        if (!file.getOriginalFilename().isEmpty()) submission.setSolution(new String(file.getBytes()));

        Contest contest = contestService.findById(contestId);

        Submission sub = new Submission();
        sub.setAuthor(user);
        sub.setSolution(submission.getSolution());
        sub.setContest(contest);
        sub.setCreationTime(LocalDateTime.now());
        sub.setLanguage(submission.getLanguage());
        int problemIndex = submission.getProblem().getIndex();
        sub.setProblem(contest.getProblems().get(problemIndex));

        submissionService.submit(sub);
        return new RedirectView("/contest/{contestId}/submissions");
    }

    @GetMapping("/contest/{contestId}/submission/{submissionId}")
    public ModelAndView loadSubmission(@AuthenticationPrincipal User user,
                                       @PathVariable int contestId,
                                       @PathVariable int submissionId) {
        Submission submission = submissionService.findById(submissionId);
        //todo: add this to service
        if (!submission.getAuthor().getUserId().equals(user.getUserId()))
            throw new SecurityException("Not enough rights to see this page");

        return new ModelAndView(VIEWS_SUBMISSION_VIEW, "submission", new SubmissionDto(submission));
    }

    @GetMapping("/contest/{contestId}/standings")
    public ModelAndView loadStandings(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        Standings standings = standingsService.getStandings(contest);
        return new ModelAndView(VIEWS_CONTEST_STANDINGS, "standings", new StandingsDto(standings));
    }
}