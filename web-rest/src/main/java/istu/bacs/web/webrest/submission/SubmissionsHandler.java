package istu.bacs.web.webrest.submission;

import istu.bacs.web.model.Submission;
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
public class SubmissionsHandler {

    private final SubmissionService submissionService;

    public SubmissionsHandler(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Bean
    public RouterFunction<ServerResponse> submissionsRouter() {
        return route(GET("/submissions"), this::getAllSubmissions)
                .andRoute(GET("/submissions/{submissionId}"), this::getSubmission);
    }

    private Mono<ServerResponse> getAllSubmissions(ServerRequest request) {
        return ok().body(
                submissionService.findAll().map(Submission::fromDb),
                Submission.class
        );
    }

    private Mono<ServerResponse> getSubmission(ServerRequest request) {
        int submissionId = Integer.parseInt(request.pathVariable("submissionId"));
        return submissionService.findById(submissionId)
                .map(Submission::fromDb)
                .flatMap(s -> ok().syncBody(s))
                .switchIfEmpty(badRequest().syncBody("Unable to find submission " + submissionId));
    }
}