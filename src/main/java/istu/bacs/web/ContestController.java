package istu.bacs.web;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.service.ContestService;
import istu.bacs.service.SubmissionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContestController {
	
	private final ContestService contestService;
	private final SubmissionService submissionService;
	
	public ContestController(ContestService contestService, SubmissionService submissionService) {
		this.contestService = contestService;
		this.submissionService = submissionService;
	}
	
	@RequestMapping("/contests")
	public List<Contest> getAllContests() {
		return contestService.findAll();
	}
	
	@RequestMapping("/contest/{contestId}")
	public Contest getContest(@PathVariable Integer contestId) {
		return contestService.findById(contestId);
	}
	
	@RequestMapping("/contest/{contestId}/submissions")
	public List<Submission> getSubmissionsForContest(@PathVariable Integer contestId) {
		return submissionService.findAllByContestId(contestId);
	}
	
}