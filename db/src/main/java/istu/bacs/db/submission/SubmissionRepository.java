package istu.bacs.db.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByContestProblem(List<ContestProblem> problems, Pageable pageable);

    @Query("SELECT s FROM Submission s WHERE s.author = :author AND s.contestProblem IN (:problems)")
    List<Submission> findAllByAuthorAndContestProblem(@Param("author") User author, @Param("problems") List<ContestProblem> problems, Pageable pageable);

    List<Submission> findAllByResultVerdict(Verdict verdict);
}