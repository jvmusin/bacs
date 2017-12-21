package istu.bacs.service;

import istu.bacs.domain.Submission;

import java.util.List;

public interface SubmissionService {
	Submission findById(int submissionId);
	List<Submission> findAll();
	List<Submission> findAllByContest(int contestId);
    List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId);
    void submit(Submission submission);
	void submitAll(List<Submission> submissions);
}