package istu.bacs.db.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    @Query("SELECT s FROM Submission s WHERE s.contestProblem IN (:problems)")
    List<Submission> findAllByContestProblems(@Param("problems") Collection<ContestProblem> problems);

    List<Submission> findAllByAuthorAndContestProblem(User author, List<ContestProblem> problems);

    @Query("SELECT s FROM Submission s WHERE s.submissionId IN (:ids)")
    List<Submission> findAllByIds(@Param("ids") List<Integer> ids);
}