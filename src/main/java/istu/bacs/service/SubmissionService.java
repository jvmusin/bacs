package istu.bacs.service;

import istu.bacs.model.Submission;

import java.util.List;

public interface SubmissionService {
	
	Submission findById(Integer id);
	List<Submission> findAllByContestId(Integer contestId);
	void save(Submission submission);
	
}