package istu.bacs.submission;

import istu.bacs.util.PlatformUnitInitializer;
import org.springframework.stereotype.Component;

import static istu.bacs.submission.Verdict.PENDING;

@Component
public class SubmissionRefresherInitializer implements PlatformUnitInitializer {

    private final SubmissionRefresher submissionRefresher;
    private final SubmissionService submissionService;

    public SubmissionRefresherInitializer(SubmissionRefresher submissionRefresher, SubmissionService submissionService) {
        this.submissionRefresher = submissionRefresher;
        this.submissionService = submissionService;
    }

    @Override
    public void init() {
        submissionRefresher.addAll(submissionService.findAllByVerdict(PENDING));
    }
}