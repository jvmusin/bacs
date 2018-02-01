package istu.bacs.db.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByResultVerdict(Verdict verdict);

    List<Submission> findAllByContestProblem(ContestProblem contestProblem);

    @Query("SELECT s FROM Submission s WHERE s.contestProblem = :contestProblem AND s.result.verdict NOT IN ('SCHEDULED', 'PENDING')")
    List<Submission> findAllByContestProblemAndChecked(@Param("contestProblem") ContestProblem contestProblem);

    List<Submission> findAllByAuthor(User author);
}