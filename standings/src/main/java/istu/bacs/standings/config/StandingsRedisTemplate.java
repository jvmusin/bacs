package istu.bacs.standings.config;

import istu.bacs.web.model.contest.standings.Standings;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class StandingsRedisTemplate extends RedisTemplate<String, Object> {

    private StandingsRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);
        setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Integer.class));
        setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Standings.class));
    }

    public StandingsRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}