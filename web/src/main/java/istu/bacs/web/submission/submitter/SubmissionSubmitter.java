package istu.bacs.web.submission.submitter;

import istu.bacs.db.submission.Submission;
import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.web.submission.SubmissionService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.db.submission.Verdict.PENDING;
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

    @Scheduled(fixedDelay = 10000)
    public void submitAll() {
        log.info("SUBMISSION SUBMITTER TICK STARTED");
        int size = q.size();
        List<Submission> all = new ArrayList<>(size);
        for (int i = 0; i < size; i++) all.add(q.poll());

        try {
            externalApi.submit(all);
        } catch (Exception e) {
            log.warning("Unable to submit problems: " + e.getMessage());
        }

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
        log.info("SUBMISSION SUBMITTER TICK FINISHED");
    }

    public void addAll(List<Submission> submissions) {
        q.addAll(submissions);
    }
}