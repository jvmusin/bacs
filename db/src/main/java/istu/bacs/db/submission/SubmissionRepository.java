package istu.bacs.db.submission;

import istu.bacs.db.contest.ContestProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByResultVerdict(Verdict verdict);

    List<Submission> findAllByContestProblem(ContestProblem contestProblem);
}