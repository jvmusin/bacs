package istu.bacs.submission;

import istu.bacs.externalapi.ExternalApiAggregator;
import org.springframework.stereotype.Service;

import java.util.List;

import static istu.bacs.submission.Verdict.PENDING;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ExternalApiAggregator externalApi;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ExternalApiAggregator externalApi) {
        this.submissionRepository = submissionRepository;
        this.externalApi = externalApi;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId)
                .map(submission -> {
                    externalApi.updateSubmissionDetails(singletonList(submission));
                    return submission;
                })
                .orElse(null);
    }

    @Override
    public List<Submission> findAllByContest(int contestId) {
        List<Submission> submissions = submissionRepository.findAllByContest_ContestId(contestId);
        prepareSubmissions(submissions);
        return submissions;
    }

    @Override
    public List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId) {
        List<Submission> submissions = submissionRepository.findAllByContest_ContestIdAndAuthor_UserId(contestId, authorUserId);
        prepareSubmissions(submissions);
        return submissions;
    }

    private void prepareSubmissions(List<Submission> submissions) {
        List<Submission> submissionsToCheck = submissions.stream()
                .filter(s -> s.getVerdict() == PENDING)
                .collect(toList());
        externalApi.updateSubmissionDetails(submissionsToCheck);
    }

    @Override
    public void submit(Submission submission) {
        externalApi.submit(submission);
        submissionRepository.save(submission);
    }
}