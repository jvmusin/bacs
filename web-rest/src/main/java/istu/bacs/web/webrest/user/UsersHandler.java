package istu.bacs.web.webrest.user;

import istu.bacs.web.model.User;
import istu.bacs.web.security.LoginHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UsersHandler {

    private final LoginHandler loginHandler;
    private final UserService userService;

    public UsersHandler(LoginHandler loginHandler, UserService userService) {
        this.loginHandler = loginHandler;
        this.userService = userService;
    }

    @Bean
    public RouterFunction<ServerResponse> usersRouter() {
        return route(POST("/users"), loginHandler::login)
                .andRoute(GET("/users"), this::getAllUsers)
                .andRoute(GET("/users/{username}"), this::getUser)
                .andRoute(PUT("/users/{username}"), this::updateUser);
    }

    private Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ok().body(
                userService.findAll().map(User::fromDb),
                User.class
        );
    }

    private Mono<ServerResponse> getUser(ServerRequest request) {
        String username = request.pathVariable("username");
        return userService.findByUsername(username)
                .map(User::fromDb)
                .flatMap(u -> ok().syncBody(u))
                .switchIfEmpty(badRequest().syncBody("Unable to find user with username '" + username + "'"));
    }

    private Mono<ServerResponse> updateUser(ServerRequest request) {
        String username = request.pathVariable("username");
        return request.bodyToMono(User.class)
                .flatMap(u -> userService.update(username, u))
                .flatMap(u -> ok().build())
                .switchIfEmpty(badRequest().syncBody("Unable to find user with username '" + username + "'"));
    }
}