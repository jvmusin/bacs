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
    private final List<Consumer<Submission>> subscribers = new CopyOnWriteArrayList<>();

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionResultRepository submissionResultRepository, ExternalApiAggregator externalApi) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
        this.externalApi = externalApi;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
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
        externalApi.submit(submission);
        submission.setResult(SubmissionResult.pending(submission.getSubmissionId()));
        save(submission);
        subscribers.forEach(f -> f.accept(submission));
    }

    @Override
    public void save(Submission submission) {
        SubmissionResult result = submission.getResult();
        submission.setResult(null);

        submissionRepository.save(submission);

        result.setSubmissionId(submission.getSubmissionId());
        submissionResultRepository.save(result);
        submission.setResult(result);
    }

    @Override
    public void subscribeOnSubmit(Consumer<Submission> function) {
        subscribers.add(function);
    }
}