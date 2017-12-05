package istu.bacs.service;

import istu.bacs.model.Submission;

import java.util.List;

public interface SubmissionService {
	Submission findById(int id);
	void submit(Submission submission, boolean pretestsOnly);
	void submitAll(List<Submission> submissions, boolean pretestsOnly);
	void updateSubmissions(List<Submission> submissions);
}