package istu.bacs.service;

import istu.bacs.model.Submission;

import java.util.List;

public interface SubmissionService {
	
	Submission findById(Integer id);
	void submit(Submission submission, boolean pretestsOnly);
	
}