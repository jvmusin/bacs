package istu.bacs.web.rest.user;

import istu.bacs.web.model.Login;
import istu.bacs.web.model.User;
import istu.bacs.web.security.LoginHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    private static final Pattern usernamePattern = Pattern.compile("[\\w\\d_-]{3,20}");
    private static final Pattern passwordPattern = Pattern.compile(".{3,20}");

    private final LoginHandler loginHandler;
    private final UserService userService;

    public UserHandler(LoginHandler loginHandler, UserService userService) {
        this.loginHandler = loginHandler;
        this.userService = userService;
    }

    @Bean
    public RouterFunction<ServerResponse> usersRouter() {
        return route(POST("/login"), loginHandler::login)
                .andRoute(GET("/users"), this::getAllUsers)
                .andRoute(POST("/users"), this::registerUser)
                .andRoute(GET("/users/{username}"), this::getUser)
                .andRoute(PUT("/users/{username}"), this::updateUser);
    }

    private Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(Login.class)
                .filter(u -> checkUsername(u.getUsername()))
                .filter(u -> checkPassword(u.getPassword()))
                .transform(userService::create)
                .transform(user -> ok().build());
    }

    private boolean checkUsername(String username) {
        return usernamePattern.matcher(username).matches();
    }

    private boolean checkPassword(String password) {
        return passwordPattern.matcher(password).matches();
    }

    private Mono<ServerResponse> getAllUsers(@SuppressWarnings("unused") ServerRequest request) {
        return ok().body(
                userService.findAll().transform(User::fromDb),
                User.class
        );
    }

    private Mono<ServerResponse> getUser(ServerRequest request) {
        String username = request.pathVariable("username");

        return Mono.just(username)
                .transform(userService::findByUsername)
                .transform(User::fromDb)
                .transform(user -> ok().body(user, User.class))
                .switchIfEmpty(badRequest().syncBody("Unable to find user with username '" + username + "'"));
    }

    private Mono<ServerResponse> updateUser(ServerRequest request) {
        String username = request.pathVariable("username");

        return request.bodyToMono(User.class)
                .filter(u -> checkUsername(u.getUsername()))
                .zipWith(Mono.just(username))
                .flatMap(t -> userService.update(t.getT2(), t.getT1()))
                .flatMap(user -> ok().build());
    }
}