package istu.bacs.background.combined;

import istu.bacs.background.combined.db.SubmissionService;
import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.rabbit.QueueName;
import istu.bacs.rabbit.RabbitService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public abstract class SubmissionProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private static final int tickDelay = 500;
    //print state once per 5 minutes
    private static final int printStateEveryNTicks = (1000 / tickDelay) * 60 * 5;
    private static final int maxSubmissionsPerBatch = Integer.MAX_VALUE;

    private final SubmissionService submissionService;
    private final RabbitService rabbitService;

    private final Queue<Integer> q = new ConcurrentLinkedDeque<>();
    private final AtomicInteger tickCount = new AtomicInteger();

    private final AtomicBoolean initialized = new AtomicBoolean();

    @SuppressWarnings("squid:S2629")
    @Scheduled(fixedDelay = tickDelay)
    public void tick() {
        if (q.isEmpty()) {
            if (tickCount.incrementAndGet() == printStateEveryNTicks) {
                log().info("Nothing to process");
                tickCount.set(0);
            }
            return;
        }

        tickCount.set(0);

        log().info("{} tick started", processorName());
        processAll();
        log().info("{} tick finished", processorName());
    }

    private void processAll() {
        log().trace("Processing started");

        Set<Integer> ids = new HashSet<>();
        while (!q.isEmpty() && ids.size() < maxSubmissionsPerBatch) ids.add(q.poll());
        log().trace("Processing {} submissions: {}", ids.size(), ids);

        try {
            List<Submission> submissions = submissionService.findAllByIds(new ArrayList<>(ids));
            log().trace("Found {} submissions", submissions.size());

            boolean anyProcessed = process(submissions);

            if (anyProcessed) {
                for (Submission submission : submissions) {
                    int submissionId = submission.getSubmissionId();
                    if (submission.getVerdict() != incomingVerdict()) {
                        submission.setContest(new Contest().withContestId(submission.getContest().getContestId()));
                        submissionService.save(submission);
                        rabbitService.send(outcomingQueueName(), submissionId);
                        log().debug("Submission {} processed: {}", submissionId, submission.getVerdict());
                    } else {
                        q.add(submissionId);
                    }
                }
            } else {
                q.addAll(ids);
            }
        } catch (Exception e) {
            log().warn("Unable to process submissions", e);
            q.addAll(ids);
        }

        log().trace("Processing finished");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized.getAndSet(true))
            return;

        submissionService.findAllByVerdict(incomingVerdict())
                .forEach(s -> q.add(s.getSubmissionId()));
        rabbitService.subscribe(incomingQueueName(), q::add);
    }

    protected abstract boolean process(List<Submission> submissions);

    protected abstract Verdict incomingVerdict();

    protected abstract QueueName incomingQueueName();

    protected abstract QueueName outcomingQueueName();

    protected abstract String processorName();

    protected abstract Logger log();
}