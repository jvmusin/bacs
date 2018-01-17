package istu.bacs.web.standings;

import istu.bacs.commons.initializer.PlatformUnitInitializer;
import istu.bacs.web.submission.SubmissionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StandingsInitializer implements PlatformUnitInitializer {

    private final StandingsService standingsService;
    private final SubmissionService submissionService;

    public StandingsInitializer(StandingsService standingsService, SubmissionService submissionService) {
        this.standingsService = standingsService;
        this.submissionService = submissionService;
    }

    @Override
    @Transactional
    public void init() {
        submissionService.findAll().forEach(standingsService::update);
    }
}