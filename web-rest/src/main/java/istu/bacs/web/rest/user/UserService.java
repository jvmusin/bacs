package istu.bacs.web.rest.user;

import istu.bacs.db.user.User;
import istu.bacs.web.model.Login;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(Mono<String> username);
    Flux<User> findAll();
    Mono<User> update(Mono<String> username, Mono<istu.bacs.web.model.User> user);
    Mono<User> update(String username, istu.bacs.web.model.User user);
    Mono<User> create(Mono<Login> user);
}