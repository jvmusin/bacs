package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.ProblemService;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private final SubmissionRepository submissionRepository;
	private final ProblemService problemService;
    private final ExternalApiAggregator externalApi;

	public SubmissionServiceImpl(SubmissionRepository submissionRepository, ProblemService problemService, ExternalApiAggregator externalApi) {
		this.submissionRepository = submissionRepository;
        this.problemService = problemService;
        this.externalApi = externalApi;
    }
	
	@Override
	public Submission findById(int id) {
		return submissionRepository.findById(id)
                .map(submission -> {
                    problemService.updateProblems(singletonList(submission.getProblem()));
                    externalApi.updateSubmissionDetails(singletonList(submission));
                    return submission;
                })
                .orElse(null);
	}

    @Override
    public List<Submission> findAll() {
        List<Submission> submissions = submissionRepository.findAll();
        List<Problem> problems = submissions.stream().map(Submission::getProblem).collect(toList());
        problemService.updateProblems(problems);
        externalApi.updateSubmissionDetails(submissions);
        return submissions;
    }

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
	    externalApi.submit(submission, pretestsOnly);
        submissionRepository.save(submission);
    }

    @Override
    public void submitAll(List<Submission> submissions, boolean pretestsOnly) {
        externalApi.submitAll(submissions, pretestsOnly);
        submissionRepository.saveAll(submissions);
    }

    @Override
    public void updateSubmissions(List<Submission> submissions) {
        externalApi.updateSubmissionDetails(submissions);
    }
}