package istu.bacs.background.combined;

import istu.bacs.background.combined.db.SubmissionService;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.externalapi.ExternalApi;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import static istu.bacs.background.combined.SubmissionSubmitterProcessor.PROCESSOR_NAME;
import static istu.bacs.db.submission.Verdict.NOT_SUBMITTED;
import static istu.bacs.rabbit.QueueNames.SCHEDULED_SUBMISSIONS;
import static istu.bacs.rabbit.QueueNames.SUBMITTED_SUBMISSIONS;

@Log
@Component(PROCESSOR_NAME)
public class SubmissionSubmitterProcessor extends SubmissionProcessor {

    static final String PROCESSOR_NAME = "SubmissionSubmitterProcessor";

    private final ExternalApi externalApi;

    public SubmissionSubmitterProcessor(SubmissionService submissionService, RabbitTemplate rabbitTemplate, ExternalApi externalApi) {
        super(submissionService, rabbitTemplate);
        this.externalApi = externalApi;
    }

    @RabbitListener(queues = SCHEDULED_SUBMISSIONS)
    public void addSubmission(int submissionId) {
        super.addSubmission(submissionId);
    }

    @Override
    protected void process(List<Submission> submissions) {
        externalApi.submit(submissions);
    }

    @Override
    protected Verdict incomingVerdict() {
        return NOT_SUBMITTED;
    }

    @Override
    protected String outcomingQueueName() {
        return SUBMITTED_SUBMISSIONS;
    }

    @Override
    protected String processorName() {
        return PROCESSOR_NAME;
    }

    @Override
    protected Logger log() {
        return log;
    }
}