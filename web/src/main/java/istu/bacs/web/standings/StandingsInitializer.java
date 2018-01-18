package istu.bacs.web.standings;

import istu.bacs.web.submission.SubmissionService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StandingsInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final StandingsService standingsService;
    private final SubmissionService submissionService;

    public StandingsInitializer(StandingsService standingsService, SubmissionService submissionService) {
        this.standingsService = standingsService;
        this.submissionService = submissionService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAll().forEach(standingsService::update);
    }
}