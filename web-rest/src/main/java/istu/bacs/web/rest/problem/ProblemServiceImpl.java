package istu.bacs.web.rest.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public Mono<Problem> findById(Mono<String> problemId) {
        return problemId
                .map(problemRepository::findById)
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    public Flux<Problem> findAll() {
        return Flux.fromIterable(problemRepository.findAll());
    }
}