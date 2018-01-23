package istu.bacs.background.combined;

import istu.bacs.db.submission.Submission;
import istu.bacs.externalapi.aggregator.ExternalApiAggregator;
import istu.bacs.background.combined.db.SubmissionService;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.db.submission.Verdict.COMPILE_ERROR;
import static istu.bacs.db.submission.Verdict.PENDING;
import static istu.bacs.rabbit.QueueNames.CHECKED_SUBMISSIONS;
import static istu.bacs.rabbit.QueueNames.SUBMITTED_SUBMISSIONS;
import static java.lang.String.format;

@Component
@Log
public class SubmissionChecker implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final String INCOMING_QUEUE_NAME = SUBMITTED_SUBMISSIONS;
    private static final String OUTCOMING_QUEUE_NAME = CHECKED_SUBMISSIONS;

    private final SubmissionService submissionService;
    private final ExternalApiAggregator externalApi;
    private final RabbitTemplate rabbitTemplate;

    private final Queue<Integer> q = new ConcurrentLinkedDeque<>();

    private ApplicationContext applicationContext;

    public SubmissionChecker(SubmissionService submissionService,
                             ExternalApiAggregator externalApi,
                             RabbitTemplate rabbitTemplate) {
        this.submissionService = submissionService;
        this.externalApi = externalApi;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = INCOMING_QUEUE_NAME)
    public void addSubmission(int submissionId) {
        q.add(submissionId);
    }

    @Scheduled(fixedDelay = 10000)
    public void tick() {
        if (q.isEmpty()) {
            log.info("NOTHING TO CHECK");
            return;
        }

        log.info("SUBMISSION CHECKER TICK STARTED");
        applicationContext.getBean(SubmissionChecker.class).checkAll();
        log.info("SUBMISSION CHECKER TICK FINISHED");
    }

    @Transactional
    public void checkAll() {
        List<Integer> ids = new ArrayList<>();
        while (!q.isEmpty()) ids.add(q.poll());

        try {
            List<Submission> submissions = submissionService.findAllByIds(ids);
            submissions.removeIf(s -> s.getVerdict() != PENDING);
            externalApi.updateSubmissionDetails(submissions);

            for (Submission submission : submissions) {
                int submissionId = submission.getSubmissionId();
                if (submission.getVerdict() != PENDING) {
                    submissionService.save(submission);
                    rabbitTemplate.convertAndSend(OUTCOMING_QUEUE_NAME, submissionId);

                    String shortInfo = submission.getVerdict().name();
                    if (submission.getVerdict() == COMPILE_ERROR)
                        shortInfo += ": " + submission.getResult().getBuildInfo();

                    log.info(format("Submission %d checked: %s", submissionId, shortInfo));
                } else {
                    log.info(format("Submission %d is in PENDING", submissionId));
                    q.add(submissionId);
                }
            }
        } catch (Exception e) {
            log.warning("Unable to check submissions: " + e.getMessage());
            e.printStackTrace();
            q.addAll(ids);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAllByVerdict(PENDING)
                .forEach(s -> q.add(s.getSubmissionId()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}