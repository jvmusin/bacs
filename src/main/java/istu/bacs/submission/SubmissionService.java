package istu.bacs.submission;

import java.util.List;
import java.util.function.Consumer;

public interface SubmissionService {
    Submission findById(int submissionId);
    List<Submission> findAll();
    List<Submission> findAllByContest(int contestId);
    List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId);
    List<Submission> findAllByVerdict(Verdict verdict);
    int submit(Submission submission);
    void save(Submission submission);

    void subscribeOnSolutionScheduled(Consumer<Submission> function);
    void solutionScheduled(Submission submission);

    void subscribeOnSolutionSubmitted(Consumer<Submission> function);
    void solutionSubmitted(Submission submission);

    void subscribeOnSolutionTested(Consumer<Submission> function);
    void solutionTested(Submission submission);
}