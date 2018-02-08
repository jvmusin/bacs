package istu.bacs.standings.config;

import istu.bacs.db.contest.ContestRepository;
import istu.bacs.standings.StandingsServiceImpl;
import istu.bacs.standings.StandingsUploader;
import istu.bacs.standings.db.ContestService;
import istu.bacs.standings.db.ContestServiceImpl;
import istu.bacs.standings.db.SubmissionService;
import istu.bacs.standings.db.SubmissionServiceImpl;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.standings.StandingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class StandingsConfiguration {

    @Bean
    StandingsRedisTemplate standingsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StandingsRedisTemplate(redisConnectionFactory);
    }

    @Bean
    SubmissionService submissionService(SubmissionRepository submissionRepository) {
        return new SubmissionServiceImpl(submissionRepository);
    }

    @Bean
    ContestService contestService(ContestRepository contestRepository) {
        return new ContestServiceImpl(contestRepository);
    }

    @Bean
    public StandingsService standingsService(StandingsRedisTemplate standingsRedisTemplate, ContestService contestService) {
        return new StandingsServiceImpl(standingsRedisTemplate, contestService);
    }

    @Bean
    @Profile("standings-updater")
    StandingsUploader standingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        return new StandingsUploader(standingsRedisTemplate, submissionService, rabbitService);
    }
}