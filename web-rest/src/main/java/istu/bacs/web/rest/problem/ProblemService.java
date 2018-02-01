package istu.bacs.web.rest.problem;

import istu.bacs.db.problem.Problem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProblemService {
    Mono<Problem> findById(Mono<String> problemId);

    Flux<Problem> findAll();

    Flux<Problem> saveAll(Flux<Problem> problems);

    Flux<Problem> saveAll(Iterable<Problem> problems);
}