package istu.bacs.standings;

import istu.bacs.standings.config.StandingsRedisTemplate;
import istu.bacs.standings.db.ContestService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;

@AllArgsConstructor
public class StandingsServiceImpl implements StandingsService {

    static final String KEY = "Standings";

    private final StandingsRedisTemplate standingsRedisTemplate;
    private final ContestService contestService;

    @Override
    public istu.bacs.web.model.get.Standings getStandings(int contestId) {
        HashOperations<String, Integer, istu.bacs.web.model.get.Standings> hash = standingsRedisTemplate.opsForHash();
        istu.bacs.web.model.get.Standings standings = hash.get(KEY, contestId);

        //noinspection ConstantConditions
        if (standings == null) {
            return new Standings(contestService.findById(contestId)).toDto();
        }

        return standings;
    }
}