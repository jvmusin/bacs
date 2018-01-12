package istu.bacs.submission;

import istu.bacs.externalapi.ExternalApiAggregator;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.submission.Verdict.PENDING;
import static java.lang.String.format;

@Component
@Log
public class SubmissionSubmitter {

    private final SubmissionService submissionService;
    private final ExternalApiAggregator externalApi;
    private final Queue<Submission> q = new ConcurrentLinkedDeque<>();

    public SubmissionSubmitter(SubmissionService submissionService, ExternalApiAggregator externalApi) {
        this.submissionService = submissionService;
        this.externalApi = externalApi;

        submissionService.subscribeOnSolutionScheduled(q::add);
    }

    @Scheduled(fixedDelay = 1000)
    public void submitAll() {
        int size = q.size();
        List<Submission> all = new ArrayList<>(size);
        for (int i = 0; i < size; i++) all.add(q.poll());

        all.forEach(submission -> {
            try {
                externalApi.submit(submission);

                submission.getResult().setVerdict(PENDING);
                submissionService.save(submission);

                submissionService.solutionSubmitted(submission);

                log.info(format("Solution %d successfully submitted", submission.getSubmissionId()));
            } catch (Exception e) {
                log.warning(format("Unable to submit solution #%d: %s", submission.getSubmissionId(), e.getMessage()));
                log.warning(e.toString());
                q.add(submission);
            }
        });   //todo: replace with submit all
    }

    public void addAll(List<Submission> submissions) {
        q.addAll(submissions);
    }
}