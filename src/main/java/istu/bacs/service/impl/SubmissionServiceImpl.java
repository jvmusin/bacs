package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Submission;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private final SubmissionRepository submissionRepository;
    private final ExternalApiAggregator externalApi;

	public SubmissionServiceImpl(SubmissionRepository submissionRepository, ExternalApiAggregator externalApi) {
		this.submissionRepository = submissionRepository;
        this.externalApi = externalApi;
    }
	
	@Override
	public Submission findById(Integer id) {
		return submissionRepository.findById(id).orElse(null);
	}

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
	    externalApi.submit(pretestsOnly, submission);
        submissionRepository.save(submission);
    }
}