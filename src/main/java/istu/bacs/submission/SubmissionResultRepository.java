package istu.bacs.submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionResultRepository extends JpaRepository<SubmissionResult, Integer> {
    List<SubmissionResult> findByVerdict(Verdict verdict);
}