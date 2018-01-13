package istu.bacs.submission;

import istu.bacs.externalapi.ExternalApiAggregator;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.submission.Verdict.PENDING;
import static java.lang.String.format;

@Service
@Log
public class SubmissionRefresher {

    private final SubmissionService submissionService;
    private final ExternalApiAggregator externalApi;
    private final Queue<Submission> q = new ConcurrentLinkedDeque<>();

    public SubmissionRefresher(SubmissionService submissionService, ExternalApiAggregator externalApi) {
        this.submissionService = submissionService;
        this.externalApi = externalApi;

        submissionService.subscribeOnSolutionSubmitted(q::add);
    }

    @Scheduled(fixedDelay = 1000)
    public void refreshAll() {
        int size = q.size();
        List<Submission> all = new ArrayList<>(size);
        for (int i = 0; i < size; i++) all.add(q.poll());

        externalApi.updateSubmissionDetails(all);

        for (Submission cur : all) {
            if (cur.getVerdict() == PENDING) q.add(cur);
            else {
                submissionService.save(cur);

                log.info(format("Solution %d successfully tested", cur.getSubmissionId()));
                submissionService.solutionTested(cur);
            }
        }
    }

    public void addAll(List<Submission> submissions) {
        q.addAll(submissions);
    }
}