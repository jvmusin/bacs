package istu.bacs.background.standingsbuilder;

import istu.bacs.background.standingsbuilder.config.StandingsRedisTemplate;
import istu.bacs.background.standingsbuilder.db.ContestService;
import istu.bacs.standingsapi.StandingsService;
import reactor.core.publisher.Mono;

public class StandingsServiceImpl implements StandingsService {

    static final String KEY = "Standings";

    private final ContestService contestService;
    private final StandingsRedisTemplate standingsRedisTemplate;

    public StandingsServiceImpl(ContestService contestService, StandingsRedisTemplate standingsRedisTemplate) {
        this.contestService = contestService;
        this.standingsRedisTemplate = standingsRedisTemplate;
    }

    @Override
    public Mono<istu.bacs.web.model.Standings> getStandings(Mono<Integer> contestId) {
        return contestId
                .flatMap(id -> {
                            istu.bacs.web.model.Standings standings = (istu.bacs.web.model.Standings) standingsRedisTemplate.opsForHash().get(KEY, id);
                            //noinspection ConstantConditions
                            if (standings == null) {
                                return contestService.findById(id)
                                        .map(Standings::new)
                                        .map(Standings::toDto)
                                        .doOnNext(s -> standingsRedisTemplate.opsForHash().put(KEY, s.getContest().getId(), s));
                            }
                            return Mono.just(standings);
                        }
                );
    }
}