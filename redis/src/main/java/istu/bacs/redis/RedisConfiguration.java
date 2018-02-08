package istu.bacs.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class RedisConfiguration {

    private static final Pattern redisUrlPattern = Pattern.compile("redis://(?<userName>.*):(?<password>.*)@(?<hostName>.+):(?<port>\\d+)");

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        String redisUrl = System.getenv("REDIS_URL");

        if (redisUrl == null)
            return new RedisStandaloneConfiguration();

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
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}