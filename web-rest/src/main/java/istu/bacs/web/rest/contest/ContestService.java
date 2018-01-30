package istu.bacs.web.rest.contest;

import istu.bacs.db.contest.Contest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContestService {
    Flux<Contest> findAll();
    Mono<Contest> findById(Mono<Integer> contestId);
    Mono<Contest> save(Mono<Contest> contest);
    Mono<Contest> delete(Mono<Contest> contest);
}