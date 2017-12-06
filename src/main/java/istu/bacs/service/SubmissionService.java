package istu.bacs.service;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.model.User;

import java.util.List;

public interface SubmissionService {
	Submission findById(int id);
	List<Submission> findAll();
	List<Submission> findAllByContest(Contest contest);
	List<Submission> findAllByContestAndAuthor(Contest contest, User author);
	void submit(Submission submission, boolean pretestsOnly);
	void submitAll(List<Submission> submissions, boolean pretestsOnly);
	void updateSubmissions(List<Submission> submissions);
}