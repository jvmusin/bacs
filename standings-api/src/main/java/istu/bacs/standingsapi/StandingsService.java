package istu.bacs.standingsapi;

import istu.bacs.web.model.Standings;
import reactor.core.publisher.Mono;

public interface StandingsService {

    Mono<Standings> getStandings(Mono<Integer> contestId);
}