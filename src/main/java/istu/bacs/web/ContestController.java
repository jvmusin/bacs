package istu.bacs.web;

import istu.bacs.service.ContestService;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
	
	private final ContestService contestService;
	private final SubmissionService submissionService;
	
	public ContestController(ContestService contestService, SubmissionService submissionService) {
		this.contestService = contestService;
		this.submissionService = submissionService;
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
	
	@RequestMapping("/contest/{contestId}/submissions")
	public String getAllSubmissionsForContest(Model model, @PathVariable Integer contestId) {
		model.addAttribute("submissions", submissionService.findAllByContestId(contestId));
		model.addAttribute("contestName", contestService.findById(contestId).getContestName());
		return VIEWS_SUBMISSION_LIST;
	}
	
}