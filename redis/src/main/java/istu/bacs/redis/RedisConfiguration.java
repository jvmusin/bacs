package istu.bacs.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Slf4j
public class RedisConfiguration {

    private static final Pattern redisUrlPattern = Pattern.compile("redis://(?<userName>.*):(?<password>.*)@(?<hostName>.+):(?<port>\\d+)");

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        String redisUrl = System.getenv("REDIS_URL");

        if (redisUrl == null) {
            log.warn("REDIS_URL IS NULL, BACKING TO DEFAULT CONFIGURATION");
            return new RedisStandaloneConfiguration();
        }

        Matcher matcher = redisUrlPattern.matcher(redisUrl);
        if (!matcher.find())
            throw new IllegalStateException("REDIS URL IS INCORRECT: " + redisUrl);

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
    @Primary
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}