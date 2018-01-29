package istu.bacs.web.rest.problem;

import istu.bacs.web.model.Problem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class ProblemHandler {

    private final ProblemService problemService;

    public ProblemHandler(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Bean
    public RouterFunction<ServerResponse> submissionsRouter() {
        return route(GET("/problems"), this::getAllProblems)
                .andRoute(GET("/problems/{problemId}"), this::getProblem);
    }

    private Mono<ServerResponse> getAllProblems(@SuppressWarnings("unused") ServerRequest request) {
        return ok().body(
                problemService.findAll().transform(Problem::fromDbProblems),
                Problem.class
        );
    }

    private Mono<ServerResponse> getProblem(ServerRequest request) {

        request.queryParam("external")
                .ifPresent(p -> log.warn("Need to implement external problem fetching"));

        String problemId = request.pathVariable("problemId");
        return Mono.just(problemId)
                .transform(problemService::findById)
                .transform(Problem::fromDbProblem)
                .transform(problem -> ok().body(problem, Problem.class))
                .switchIfEmpty(badRequest().syncBody("Unable to find problem with id '" + problemId + "'"));
    }
}