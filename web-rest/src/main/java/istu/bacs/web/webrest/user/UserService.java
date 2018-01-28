package istu.bacs.web.webrest.user;

import istu.bacs.db.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);
    Flux<User> findAll();
    Mono<User> update(String username, istu.bacs.web.model.User user);
}