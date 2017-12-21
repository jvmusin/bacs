package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.domain.Contest;
import istu.bacs.domain.Submission;
import istu.bacs.domain.User;
import istu.bacs.domain.Verdict;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Submission> findAll() {
        List<Submission> submissions = submissionRepository.findAll();
        prepareSubmissions(submissions);
        return submissions;
    }

    @Override
    public List<Submission> findAllByContest(Contest contest) {
        List<Submission> submissions = submissionRepository.findAllByContest(contest);
        prepareSubmissions(submissions);
        return submissions;
    }

    @Override
    public List<Submission> findAllByContestAndAuthor(Contest contest, User author) {
        List<Submission> submissions = submissionRepository.findAllByContestAndAuthor(contest, author);
        prepareSubmissions(submissions);
        return submissions;
    }

    private void prepareSubmissions(List<Submission> submissions) {
        submissions = submissions.stream()
                .filter(sub -> sub.getVerdict() == Verdict.PENDING)
                .collect(toList());
        externalApi.updateSubmissionDetails(submissions);
    }

    @Override
    public void submit(Submission submission) {
        externalApi.submit(submission);
        submissionRepository.save(submission);
    }

    @Override
    public void submitAll(List<Submission> submissions) {
        externalApi.submitAll(submissions);
        submissionRepository.saveAll(submissions);
    }
}