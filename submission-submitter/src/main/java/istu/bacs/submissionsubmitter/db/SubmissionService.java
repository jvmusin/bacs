package istu.bacs.submissionsubmitter.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;

import java.util.List;

public interface SubmissionService {
    List<Submission> findAllByVerdict(Verdict verdict);
    List<Submission> findAllByIds(List<Integer> ids);
    void save(Submission submission);
}