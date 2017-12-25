package istu.bacs.submission;

import istu.bacs.externalapi.ExternalApiAggregator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.submission.Verdict.PENDING;

@Service
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
            else submissionService.solutionTested(cur);
        }
    }
}