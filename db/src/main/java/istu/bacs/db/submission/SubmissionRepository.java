package istu.bacs.db.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByContestProblem(List<ContestProblem> problems, Pageable pageable);
    List<Submission> findAllByAuthorAndContestProblem(User author, List<ContestProblem> problems, Pageable pageable);
}