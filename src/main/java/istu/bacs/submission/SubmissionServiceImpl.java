package istu.bacs.submission;

import istu.bacs.externalapi.ExternalApiAggregator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;
    private final ExternalApiAggregator externalApi;

    private final List<Consumer<Submission>> onSubmittedSubscribers = new CopyOnWriteArrayList<>();
    private final List<Consumer<Submission>> onTestedSubscribers = new CopyOnWriteArrayList<>();

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionResultRepository submissionResultRepository, ExternalApiAggregator externalApi) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
        this.externalApi = externalApi;

        subscribeOnSolutionTested(this::save);
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
    public void submit(Submission submission) {
        //todo: If sybon fall down, we also fail =(
        externalApi.submit(submission);
        submission.setResult(SubmissionResult.pending(submission));
        save(submission);
        solutionSubmitted(submission);
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
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