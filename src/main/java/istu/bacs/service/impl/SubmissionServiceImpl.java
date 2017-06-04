package istu.bacs.service.impl;

import istu.bacs.model.Submission;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {
	
	private final Map<Integer, Submission> submissionById = new HashMap<>();
	
	@Override
	public Submission findById(Integer id) {
		return submissionById.get(id);
	}
	
	@Override
	public List<Submission> findAllByContestId(Integer contestId) {
		return submissionById.values().stream()
				.filter(s -> Objects.equals(s.getContest().getContestId(), contestId))
				.collect(Collectors.toList());
	}
	
	@Override
	public void save(Submission submission) {
		submissionById.put(submission.getSubmissionId(), submission);
	}
	
}