package istu.bacs.web.rest.problem;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.model.Problem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class ProblemHandler {

    private final ProblemService problemService;
    private final ExternalApi externalApi;

    public ProblemHandler(ProblemService problemService, ExternalApi externalApi) {
        this.problemService = problemService;
        this.externalApi = externalApi;
    }

    @Bean
    public RouterFunction<ServerResponse> problemRouter() {
        return route(GET("/problems"), this::getAllProblems)
                .andRoute(GET("/problems/{problemId}"), this::getProblem);
    }

    private Mono<ServerResponse> getAllProblems(@SuppressWarnings("unused") ServerRequest request) {
        request.queryParam("external")
                .map(val -> externalApi.getAllProblems())
                .ifPresent(problemService::saveAll);

        return ok().body(
                problemService.findAll().transform(Problem::fromDbProblems),
                Problem.class
        );
    }

    private Mono<ServerResponse> getProblem(ServerRequest request) {
        String problemId = request.pathVariable("problemId");

        return Mono.just(problemId)
                .transform(problemService::findById)
                .transform(Problem::fromDbProblem)
                .transform(problem -> ok().body(problem, Problem.class))
                .switchIfEmpty(badRequest().syncBody("Unable to find problem with id '" + problemId + "'"));
    }
}