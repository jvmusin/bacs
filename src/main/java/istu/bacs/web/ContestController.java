package istu.bacs.web;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.model.User;
import istu.bacs.model.type.Language;
import istu.bacs.model.type.Verdict;
import istu.bacs.service.ContestService;
import istu.bacs.service.SubmissionService;
import istu.bacs.sybon.SybonApi;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
	private static final String VIEWS_SUBMIT_PAGE = "contests/submissions/submit-solution";
	
	private final ContestService contestService;
	private final SubmissionService submissionService;
	private final SybonApi sybon;
	
	public ContestController(ContestService contestService, SubmissionService submissionService, SybonApi sybon) {
		this.contestService = contestService;
		this.submissionService = submissionService;
        this.sybon = sybon;
    }
	
	@RequestMapping("/contests")
	public String getAllContests(Model model) {
		model.addAttribute("contests", contestService.findAll());
		return VIEWS_CONTEST_LIST;
	}
	
	@RequestMapping("/contest/{contestId}")
	public String getContest(Model model, @PathVariable Integer contestId) {
		model.addAttribute("contest", contestService.findById(contestId));
		return VIEWS_CONTEST_PROBLEMS;
	}

    @RequestMapping("/contest/{contestId}/{problemId}")
    public String loadStatement(@PathVariable Integer contestId, @PathVariable Integer problemId) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemId - 1);
        return "redirect:" + sybon.getStatementUrl(problem.getProblemId());
    }
	
	@RequestMapping("/contest/{contestId}/submissions")
	public String getAllSubmissionsForContest(Model model, @PathVariable Integer contestId) {
		model.addAttribute("submissions", submissionService.findAllByContestId(contestId));
		model.addAttribute("contestName", contestService.findById(contestId).getContestName());
		return VIEWS_SUBMISSION_LIST;
	}
	
	@GetMapping("/contest/{contestId}/submit")
	public String loadSubmissionForm(Model model, @PathVariable int contestId) {
		model.addAttribute("submission", new Submission());
		model.addAttribute("languages", Language.values());
		model.addAttribute("problems", contestService.findById(contestId).getProblems());
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
		submission.setVerdict(Verdict.Accepted);
		submission.setTimeConsumedMillis(1.23 * 1000);
		submission.setMemoryConsumedBytes(21.4 * 1024 * 1024);
		submissionService.save(submission);
		return "redirect:/contest/{contestId}/submissions";
	}
	
}