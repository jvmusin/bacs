package istu.bacs.web;

import istu.bacs.model.Submission;
import istu.bacs.model.User;
import istu.bacs.service.SubmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SubmissionController {
	
	private static final String VIEWS_SUBMISSION_VIEW = "contests/submissions/submission-view";
	
	private final SubmissionService submissionService;
	
	public SubmissionController(SubmissionService submissionService) {
		this.submissionService = submissionService;
	}
	
	@RequestMapping("/submission/{submissionId}")
	public String getSubmission(@AuthenticationPrincipal User user, Model model, @PathVariable int submissionId) {
		Submission submission = submissionService.findById(submissionId);
		if (!submission.getAuthor().getUsername().equals(user.getUsername()))
			throw new SecurityException("Not enough rights to see this page");
		model.addAttribute(submission);
		return VIEWS_SUBMISSION_VIEW;
	}
}