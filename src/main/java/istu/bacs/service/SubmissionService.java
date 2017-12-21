package istu.bacs.service;

import istu.bacs.domain.Contest;
import istu.bacs.domain.Submission;
import istu.bacs.domain.User;

import java.util.List;

public interface SubmissionService {
	Submission findById(int submissionId);
	List<Submission> findAll();
	List<Submission> findAllByContest(Contest contest);
	List<Submission> findAllByContestAndAuthor(Contest contest, User author);
	void submit(Submission submission);
	void submitAll(List<Submission> submissions);
}