package istu.bacs.background.combined;

import istu.bacs.background.combined.db.SubmissionService;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.rabbit.QueueName;
import istu.bacs.rabbit.RabbitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.lang.String.format;

public abstract class SubmissionProcessor implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final int tickDelay = 500;
    //print state once per 5 minutes
    private static final int printStateEveryNTicks = (1000 / tickDelay) * 60 * 5;

    private final SubmissionService submissionService;
    private final RabbitService rabbitService;

    private final Queue<Integer> q = new ConcurrentLinkedDeque<>();
    private final AtomicInteger tickCount = new AtomicInteger();

    private SubmissionProcessor self;
    private AtomicBoolean initialized = new AtomicBoolean();

    public SubmissionProcessor(SubmissionService submissionService, RabbitService rabbitService) {
        this.submissionService = submissionService;
        this.rabbitService = rabbitService;
    }

    private void addSubmission(int submissionId) {
        q.add(submissionId);
    }

    @PostConstruct
    private void registerSubmissionReceiver() {
        rabbitService.subscribe(incomingQueueName(), this::addSubmission);
    }

    @Scheduled(fixedDelay = tickDelay)
    public void tick() {
        if (q.isEmpty()) {
            if (tickCount.incrementAndGet() == printStateEveryNTicks) {
                log().info("NOTHING TO PROCESS");
                tickCount.set(0);
            }
            return;
        }

        tickCount.set(0);

        log().info(processorName() + " TICK STARTED");
        self.processAll();
        log().info(processorName() + " TICK FINISHED");
    }

    @Transactional
    public void processAll() {
        List<Integer> ids = new ArrayList<>();
        while (!q.isEmpty()) ids.add(q.poll());

        try {
            List<Submission> submissions = submissionService.findAllByIds(ids);
            submissions.removeIf(s -> s.getVerdict() != incomingVerdict());

            process(submissions);

            for (Submission submission : submissions) {
                int submissionId = submission.getSubmissionId();
                if (submission.getVerdict() != incomingVerdict()) {
                    submissionService.save(submission);
                    rabbitService.send(outcomingQueueName(), submissionId);
                    log().info(format("Submission %d processed: %s", submissionId, submission.getVerdict()));
                } else {
                    log().info(format("Submission %d NOT processed: %s", submissionId, incomingVerdict()));
                    q.add(submissionId);
                }
            }
        } catch (Exception e) {
            log().warning("Unable to process submissions: " + e.getMessage());
            e.printStackTrace();
            q.addAll(ids);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        self = applicationContext.getBean(processorName(), SubmissionProcessor.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized.getAndSet(true))
            return;

        submissionService.findAllByVerdict(incomingVerdict())
                .forEach(s -> q.add(s.getSubmissionId()));
    }

    protected abstract void process(List<Submission> submissions);

    protected abstract Verdict incomingVerdict();

    protected abstract QueueName incomingQueueName();

    protected abstract QueueName outcomingQueueName();

    protected abstract String processorName();

    protected abstract Logger log();
}