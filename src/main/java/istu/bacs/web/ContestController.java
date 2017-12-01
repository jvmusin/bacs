package istu.bacs.web;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.model.User;
import istu.bacs.model.Language;
import istu.bacs.service.ContestService;
import istu.bacs.service.SubmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
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
	public String getAllContests(Model model) {
		model.addAttribute("contests", contestService.findAll());
		return VIEWS_CONTEST_LIST;
	}
	
	@RequestMapping("/contest/{contestId}")
	public String getContest(Model model, @PathVariable Integer contestId) {
        Contest contest = contestService.findById(contestId);
        externalApi.updateProblemDetails(contest.getProblems());
        model.addAttribute("contest", contest);
		return VIEWS_CONTEST_PROBLEMS;
	}

    @RequestMapping("/contest/{contestId}/{problemNumber}")
    public String loadStatement(@PathVariable Integer contestId, @PathVariable Integer problemNumber) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemNumber - 1);
        return "redirect:" + externalApi.getStatementUrl(problem.getProblemId());
    }
	
	@RequestMapping("/contest/{contestId}/submissions")
	public String getAllSubmissionsForContest(Model model, @PathVariable Integer contestId) {
        Contest contest = contestService.findById(contestId);
        externalApi.updateContest(contest);

        model.addAttribute("submissions", contest.getSubmissions());
		model.addAttribute("contestName", contest.getContestName());
		return VIEWS_SUBMISSION_LIST;
	}
	
	@GetMapping("/contest/{contestId}/submit")
	public String loadSubmissionForm(Model model, @PathVariable int contestId) {
		model.addAttribute("submission", new Submission());
		model.addAttribute("languages", Language.values());
        List<Problem> problems = contestService.findById(contestId).getProblems();
        externalApi.updateProblemDetails(problems);
        model.addAttribute("problems", problems);
		return VIEWS_SUBMIT_PAGE;
	}
	
	@PostMapping("/contest/{contestId}/submit")
	public String submit(@ModelAttribute Submission submission,
                         @PathVariable Integer contestId,
                         @RequestParam MultipartFile file,
                         @AuthenticationPrincipal User user) throws IOException {
		if (!file.getOriginalFilename().isEmpty())
			submission.setSolution(new String(file.getBytes()));
		submission.setAuthor(user);
        submission.setContest(contestService.findById(contestId));
        submission.setCreationTime(LocalDateTime.now());
        submissionService.submit(submission, false);
		return "redirect:/contest/{contestId}/submissions";
	}
	
}