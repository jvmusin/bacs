package istu.bacs.submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findAllByContest_ContestId(int contestId);
    List<Submission> findAllByContest_ContestIdAndAuthor_UserId(int contestId, int authorUserId);
}