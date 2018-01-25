package istu.bacs.background.standingsbuilder.config;

import istu.bacs.background.standingsbuilder.StandingsServiceImpl;
import istu.bacs.background.standingsbuilder.StandingsUpdater;
import istu.bacs.background.standingsbuilder.db.SubmissionService;
import istu.bacs.background.standingsbuilder.db.SubmissionServiceImpl;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.standingsapi.StandingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class StandingsBuilderConfiguration {

    @Bean
    StandingsRedisTemplate standingsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StandingsRedisTemplate(redisConnectionFactory);
    }

    @Bean
    SubmissionService submissionService(SubmissionRepository submissionRepository) {
        return new SubmissionServiceImpl(submissionRepository);
    }

    @Bean
    public StandingsService standingsService(StandingsRedisTemplate standingsRedisTemplate) {
        return new StandingsServiceImpl(standingsRedisTemplate);
    }

    @Bean
    @Profile("standings-updater")
    StandingsUpdater standingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        return new StandingsUpdater(standingsRedisTemplate, submissionService, rabbitService);
    }
}