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
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class StandingsBuilderConfiguration {

    private static final Pattern redisUrlPattern = Pattern.compile("redis://(?<userName>.*):(?<password>.*)@(?<hostName>.+):(?<port>\\d+)");

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        String redisUrl = System.getenv("REDIS_URL");

        if (redisUrl == null)
            return new RedisStandaloneConfiguration();

        Matcher matcher = redisUrlPattern.matcher(redisUrl);
        if (!matcher.find())
            throw new RuntimeException("REDIS URL IS INCORRECT: " + redisUrl);

        String password = matcher.group("password");
        String hostName = matcher.group("hostName");
        int port = Integer.parseInt(matcher.group("port"));

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setHostName(hostName);
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public StandingsRedisTemplate standingsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StandingsRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public SubmissionService submissionService(SubmissionRepository submissionRepository) {
        return new SubmissionServiceImpl(submissionRepository);
    }

    @Bean
    public StandingsService standingsService(StandingsRedisTemplate standingsRedisTemplate) {
        return new StandingsServiceImpl(standingsRedisTemplate);
    }

    @Bean
    @Profile("standings-updater")
    public StandingsUpdater standingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        return new StandingsUpdater(standingsRedisTemplate, submissionService, rabbitService);
    }
}