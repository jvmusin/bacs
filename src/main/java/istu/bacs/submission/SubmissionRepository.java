package istu.bacs.submission;

import istu.bacs.contest.ContestProblem;
import istu.bacs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    @Query("SELECT s FROM Submission s WHERE s.contestProblem IN (:problems)")
    List<Submission> findAllByContestProblems(@Param("problems") Collection<ContestProblem> problems);
    List<Submission> findAllByAuthorAndContestProblem(User author, List<ContestProblem> problems);
}