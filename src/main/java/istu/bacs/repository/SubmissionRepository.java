package istu.bacs.repository;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
	List<Submission> findAllByContest(Contest contest);
	List<Submission> findAllByContestAndAuthor(Contest contest, User author);
}