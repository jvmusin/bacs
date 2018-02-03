package istu.bacs.background.standingsbuilder.db;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestRepository;
import reactor.core.publisher.Mono;

public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public Mono<Contest> findById(int contestId) {
        return Mono.just(contestId)
                .map(contestRepository::findById)
                .flatMap(Mono::justOrEmpty);
    }
}