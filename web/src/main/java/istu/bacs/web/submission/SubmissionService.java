package istu.bacs.web.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;

import java.util.List;
import java.util.function.Consumer;

public interface SubmissionService {
    Submission findById(int submissionId);
    List<Submission> findAll();
    List<Submission> findAllByContest(int contestId);
    List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId);
    List<Submission> findAllByVerdict(Verdict verdict);

    int submit(EnhancedSubmitSolutionDto submission);
    void save(Submission submission);

    void subscribeOnSolutionScheduled(Consumer<Submission> function);
    void solutionScheduled(Submission submission);

    void subscribeOnSolutionSubmitted(Consumer<Submission> function);
    void solutionSubmitted(Submission submission);

    void subscribeOnSolutionTested(Consumer<Submission> function);
    void solutionTested(Submission submission);
}