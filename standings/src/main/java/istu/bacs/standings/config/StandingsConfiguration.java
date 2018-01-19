package istu.bacs.standings.config;

import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.standings.db.SubmissionServiceImpl;
import istu.bacs.standings.service.StandingsService;
import istu.bacs.standings.service.StandingsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StandingsConfiguration {

    @Bean
    public StandingsService standingsService(SubmissionRepository submissionRepository) {
        return new StandingsServiceImpl(new SubmissionServiceImpl(submissionRepository));
    }
}