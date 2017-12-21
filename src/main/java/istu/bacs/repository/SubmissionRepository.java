package istu.bacs.repository;

import istu.bacs.domain.Contest;
import istu.bacs.domain.Submission;
import istu.bacs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
	List<Submission> findAllByContest(Contest contest);
	List<Submission> findAllByContestAndAuthor(Contest contest, User author);
}