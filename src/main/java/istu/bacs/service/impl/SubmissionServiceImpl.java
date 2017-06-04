package istu.bacs.service.impl;

import istu.bacs.model.Submission;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubmissionServiceImpl implements SubmissionService {
	
	private final Map<Integer, Submission> submissionById = new HashMap<>();
	
	@Override
	public Submission findById(Integer id) {
		return submissionById.get(id);
	}
	
	@Override
	public void save(Submission submission) {
		submissionById.put(submission.getSubmissionId(), submission);
	}
	
}