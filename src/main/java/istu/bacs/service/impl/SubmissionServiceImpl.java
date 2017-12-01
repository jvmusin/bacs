package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private final SubmissionRepository submissionRepository;
	private final ProblemService problemService;
	private final ContestService contestService;
	private final ExternalApiAggregator externalApi;

	public SubmissionServiceImpl(SubmissionRepository submissionRepository, ProblemService problemService, ContestService contestService, ExternalApiAggregator externalApi) {
		this.submissionRepository = submissionRepository;
        this.problemService = problemService;
        this.contestService = contestService;
        this.externalApi = externalApi;
    }
	
	@Override
	public Submission findById(Integer id) {
		return submissionRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Submission> findAllByContestId(Integer contestId) {
		Contest contest = contestService.findById(contestId);
        List<Submission> submissions = contest.getSubmissions();
        externalApi.updateSubmissionResults(submissions);
        return submissions;
	}

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
	    externalApi.submit(pretestsOnly, submission);
        submissionRepository.save(submission);
    }
}