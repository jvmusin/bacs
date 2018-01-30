package istu.bacs.web.rest.contest;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public Flux<Contest> findAll() {
        return Flux.fromIterable(contestRepository.findAll());
    }

    @Override
    public Mono<Contest> findById(Mono<Integer> contestId) {
        return contestId.map(contestRepository::findById)
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    public Mono<Contest> save(Mono<Contest> contest) {
        return contest.map(contestRepository::save);
    }

    @Override
    public Mono<Contest> delete(Mono<Contest> contest) {
        return contest.doOnNext(contestRepository::delete);
    }
}