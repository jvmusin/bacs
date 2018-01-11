package istu.bacs.submission;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    private final List<Consumer<Submission>> onScheduledSubscribers = Collections.synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onSubmittedSubscribers = Collections.synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onTestedSubscribers = Collections.synchronizedList(new ArrayList<>());

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
    }

    @Override
    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> findAllByContest(int contestId) {
        return submissionRepository.findAllByContest_ContestId(contestId);
    }

    @Override
    public List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId) {
        return submissionRepository.findAllByContest_ContestIdAndAuthor_UserId(contestId, authorUserId);
    }

    @Override
    public int submit(Submission submission) {
        save(submission);
        solutionScheduled(submission);
        return submission.getSubmissionId();
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }

    @Override
    public void subscribeOnSolutionScheduled(Consumer<Submission> function) {
        onScheduledSubscribers.add(function);
    }

    @Override
    public void solutionScheduled(Submission submission) {
        onScheduledSubscribers.forEach(f -> f.accept(submission));
    }

    @Override
    public void subscribeOnSolutionSubmitted(Consumer<Submission> function) {
        onSubmittedSubscribers.add(function);
    }

    @Override
    public void solutionSubmitted(Submission submission) {
        onSubmittedSubscribers.forEach(f -> f.accept(submission));
    }

    @Override
    public void subscribeOnSolutionTested(Consumer<Submission> function) {
        onTestedSubscribers.add(function);
    }

    @Override
    public void solutionTested(Submission submission) {
        onTestedSubscribers.forEach(f -> f.accept(submission));
    }
}