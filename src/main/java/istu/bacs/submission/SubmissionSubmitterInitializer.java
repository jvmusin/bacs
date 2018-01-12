package istu.bacs.submission;

import istu.bacs.util.PlatformUnitInitializer;
import org.springframework.stereotype.Component;

import static istu.bacs.submission.Verdict.NOT_SUBMITTED;

@Component
public class SubmissionSubmitterInitializer implements PlatformUnitInitializer {

    private final SubmissionSubmitter submissionSubmitter;
    private final SubmissionService submissionService;

    public SubmissionSubmitterInitializer(SubmissionSubmitter submissionSubmitter, SubmissionService submissionService) {
        this.submissionSubmitter = submissionSubmitter;
        this.submissionService = submissionService;
    }

    @Override
    public void init() {
        submissionSubmitter.addAll(submissionService.findAllByVerdict(NOT_SUBMITTED));
    }
}