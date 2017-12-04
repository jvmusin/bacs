package istu.bacs.web;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.externalapi.ExternalApiHelper;
import istu.bacs.model.*;
import istu.bacs.service.ContestService;
import istu.bacs.service.SubmissionService;
import istu.bacs.web.dto.*;
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
import java.util.Set;

import static java.util.Collections.singletonList;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
    private static final String VIEWS_SUBMISSION_VIEW = "contests/submissions/submission-view";
    private static final String VIEWS_SUBMIT_PAGE = "contests/submissions/submit-solution";

	private final ContestService contestService;
	private final SubmissionService submissionService;
	private final ExternalApiAggregator externalApi;
	
	public ContestController(ContestService contestService, SubmissionService submissionService, ExternalApiAggregator externalApi) {
		this.contestService = contestService;
		this.submissionService = submissionService;
        this.externalApi = externalApi;
    }
	
	@RequestMapping("/contests")
	public ModelAndView loadAllContests() {
        List<Contest> contests = contestService.findAll();
        return new ModelAndView(VIEWS_CONTEST_LIST, "model", new ContestListDto(contests));
    }
	
	@RequestMapping("/contest/{contestId}")
	public ModelAndView loadContestProblems(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return new ModelAndView(VIEWS_CONTEST_PROBLEMS, "contest", ContestDto.withProblems(contest));
	}

    @RequestMapping("/contest/{contestId}/problem/{problemLetter}")
    public RedirectView loadStatement(@PathVariable int contestId, @PathVariable char problemLetter) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemLetter - 'A');
        return new RedirectView(externalApi.getStatementUrl(problem.getProblemId()).toString());
    }
	
	@RequestMapping("/contest/{contestId}/submissions")
	public ModelAndView loadContestSubmissions(@PathVariable int contestId, @AuthenticationPrincipal User user) {
        Contest contest = contestService.findById(contestId);

        List<Submission> submissions = contest.getSubmissions();
        submissions.removeIf(s -> s.getAuthor().getUserId() != (int) user.getUserId());
        externalApi.updateSubmissionResults(submissions);

        return new ModelAndView(VIEWS_SUBMISSION_LIST, "model", new ContestSubmissionsDto(contest.getContestName(), submissions));
	}
	
	@GetMapping("/contest/{contestId}/submit")
	public ModelAndView loadSubmissionForm(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        List<Problem> problems = contest.getProblems();

        return new ModelAndView(VIEWS_SUBMIT_PAGE, "model",
                new SubmissionFormDto(contestId, EnumSet.allOf(Language.class), problems));
    }
	
	@PostMapping("/contest/{contestId}/submit")
	public RedirectView submit(@ModelAttribute SubmissionDto submission,
                         @PathVariable int contestId,
                         @RequestParam MultipartFile file,
                         @AuthenticationPrincipal User user) throws IOException {
	    //add user checking to controller
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

        submissionService.submit(sub, false);
        return new RedirectView("/contest/{contestId}/submissions");
	}

    @RequestMapping("/submission/{submissionId}")
    public ModelAndView loadSubmission(@AuthenticationPrincipal User user, @PathVariable int submissionId) {
        Submission submission = submissionService.findById(submissionId);
        //todo: add this to controller
        if (!submission.getAuthor().getUserId().equals(user.getUserId()))
            throw new SecurityException("Not enough rights to see this page");

        externalApi.updateSubmissionResults(singletonList(submission));
        externalApi.updateProblemDetails(singletonList(submission.getProblem()));

        return new ModelAndView(VIEWS_SUBMISSION_VIEW, "submission", new SubmissionDto(submission));
    }
}