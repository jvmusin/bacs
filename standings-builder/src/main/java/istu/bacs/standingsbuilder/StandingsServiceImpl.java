package istu.bacs.standingsbuilder;

import istu.bacs.standingsapi.StandingsService;
import istu.bacs.standingsapi.dto.StandingsDto;
import istu.bacs.standingsbuilder.config.StandingsRedisTemplate;
import org.springframework.data.redis.core.HashOperations;

public class StandingsServiceImpl implements StandingsService {

    public static final String KEY = "Standings";

    private final StandingsRedisTemplate standingsRedisTemplate;

    public StandingsServiceImpl(StandingsRedisTemplate standingsRedisTemplate) {
        this.standingsRedisTemplate = standingsRedisTemplate;
    }

    @Override
    public StandingsDto getStandings(int contestId) {
        HashOperations<String, Integer, StandingsDto> hash = standingsRedisTemplate.opsForHash();
        StandingsDto standings = hash.get(KEY, contestId);

        //noinspection ConstantConditions
        if (standings == null) {
            return new Standings().toDto();
        }

        return standings;
    }
}