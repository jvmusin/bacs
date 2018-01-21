package istu.bacs.submissionsubmitterandchecker;

import istu.bacs.db.submission.Submission;
import istu.bacs.externalapi.aggregator.ExternalApiAggregator;
import istu.bacs.submissionsubmitterandchecker.db.SubmissionService;
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

import static istu.bacs.db.submission.Verdict.NOT_SUBMITTED;
import static istu.bacs.rabbit.QueueNames.SCHEDULED_SUBMISSIONS;
import static istu.bacs.rabbit.QueueNames.SUBMITTED_SUBMISSIONS;
import static java.lang.String.format;

@Component
@Log
public class SubmissionSubmitter implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final String INCOMING_QUEUE_NAME = SCHEDULED_SUBMISSIONS;
    private static final String OUTCOMING_QUEUE_NAME = SUBMITTED_SUBMISSIONS;

    private final SubmissionService submissionService;
    private final ExternalApiAggregator externalApi;
    private final RabbitTemplate rabbitTemplate;

    private final Queue<Integer> q = new ConcurrentLinkedDeque<>();

    private ApplicationContext applicationContext;

    public SubmissionSubmitter(SubmissionService submissionService,
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
            log.info("NOTHING TO SUBMIT");
            return;
        }

        log.info("SUBMISSION SUBMITTER TICK STARTED");
        applicationContext.getBean(SubmissionSubmitter.class).submitAll();
        log.info("SUBMISSION SUBMITTER TICK FINISHED");
    }

    @Transactional
    public void submitAll() {
        List<Integer> ids = new ArrayList<>();
        while (!q.isEmpty()) ids.add(q.poll());

        try {
            List<Submission> submissions = submissionService.findAllByIds(ids);
            submissions.removeIf(s -> s.getVerdict() != NOT_SUBMITTED);
            externalApi.submit(submissions);

            for (Submission submission : submissions) {
                int submissionId = submission.getSubmissionId();
                if (submission.getVerdict() != NOT_SUBMITTED) {
                    submissionService.save(submission);
                    rabbitTemplate.convertAndSend(OUTCOMING_QUEUE_NAME, submissionId);
                    log.info(format("Submission %d submitted", submissionId));
                } else {
                    log.warning(format("Submission %d is not submitted yet", submissionId));
                    q.add(submissionId);
                }
            }
        } catch (Exception e) {
            log.warning("Unable to submit submissions: " + e.getMessage());
            e.printStackTrace();
            q.addAll(ids);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAllByVerdict(NOT_SUBMITTED)
                .forEach(s -> q.add(s.getSubmissionId()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}