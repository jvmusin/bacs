package istu.bacs.web;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Submission;
import istu.bacs.model.User;
import istu.bacs.service.SubmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

import static java.util.Collections.singletonList;

@Controller
public class SubmissionController {
	
	private static final String VIEWS_SUBMISSION_VIEW = "contests/submissions/submission-view";
	
	private final SubmissionService submissionService;
	private final ExternalApiAggregator externalApi;
	
	public SubmissionController(SubmissionService submissionService, ExternalApiAggregator externalApi) {
		this.submissionService = submissionService;
        this.externalApi = externalApi;
    }
	
	@RequestMapping("/submission/{submissionId}")
	public String getSubmission(@AuthenticationPrincipal User user, Model model, @PathVariable int submissionId) {
		Submission submission = submissionService.findById(submissionId);
		if (!submission.getAuthor().getUserId().equals(user.getUserId()))
			throw new SecurityException("Not enough rights to see this page");

		externalApi.updateSubmissionResults(singletonList(submission));
		externalApi.updateProblemDetails(singletonList(submission.getProblem()));

		model.addAttribute(submission);
		return VIEWS_SUBMISSION_VIEW;
	}
}