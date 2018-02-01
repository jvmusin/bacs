package istu.bacs.background.standingsbuilder.db;

import istu.bacs.db.contest.Contest;
import reactor.core.publisher.Mono;

public interface ContestService {
    Mono<Contest> findById(Mono<Integer> contestId);
}