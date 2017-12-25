package istu.bacs.standings;

import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

import static istu.bacs.submission.Verdict.PENDING;
import static java.util.stream.Collectors.toList;

@Component
public class StandingsInitializer implements InitializingBean {

    private final StandingsService standingsService;
    private final SubmissionService submissionService;

    public StandingsInitializer(StandingsService standingsService, SubmissionService submissionService) {
        this.standingsService = standingsService;
        this.submissionService = submissionService;
    }

    @Override
    public void afterPropertiesSet() {
        List<Submission> submissions = submissionService.findAll().stream()
                .filter(sub -> sub.getVerdict() != PENDING).collect(toList());
        standingsService.update(submissions);
    }
}