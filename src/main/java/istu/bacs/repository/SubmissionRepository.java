package istu.bacs.repository;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
	List<Submission> findAllByContest(Contest contest);
}