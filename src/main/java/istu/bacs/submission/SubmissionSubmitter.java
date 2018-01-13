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

        externalApi.submit(all);

        for (Submission cur : all) {
            if (cur.getExternalSubmissionId() == null) {
                log.warning("Unable to submit solution " + cur.getSubmissionId());
                q.add(cur);
            } else {
                cur.getResult().setVerdict(PENDING);
                submissionService.save(cur);

                log.info(format("Solution %d successfully submitted", cur.getSubmissionId()));
                submissionService.solutionSubmitted(cur);
            }
        }
    }

    public void addAll(List<Submission> submissions) {
        q.addAll(submissions);
    }
}