package istu.bacs.submission;

import java.util.List;
import java.util.function.Consumer;

public interface SubmissionService {
    Submission findById(int submissionId);
    List<Submission> findAllByContest(int contestId);
    List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId);
    void submit(Submission submission);
    void save(Submission submission);

    void subscribeOnSubmit(Consumer<Submission> function);
}