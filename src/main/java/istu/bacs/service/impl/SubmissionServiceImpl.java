package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
	
	private final SubmissionRepository submissionRepository;
	
	public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
		this.submissionRepository = submissionRepository;
	}
	
	@Override
	public Submission findById(Integer id) {
		return submissionRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Submission> findAllByContestId(Integer contestId) {
		Contest contest = new Contest();
		contest.setContestId(contestId);
		return submissionRepository.findAllByContest(contest);
	}
	
	@Override
	public void save(Submission submission) {
		submissionRepository.save(submission);
	}
	
}