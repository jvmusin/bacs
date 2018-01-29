package istu.bacs.web.model;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Value
public class User {
    String username;

    public static Mono<User> fromDb(Mono<istu.bacs.db.user.User> user) {
        return user.map(User::convert);
    }

    public static Flux<User> fromDb(Flux<istu.bacs.db.user.User> user) {
        return user.map(User::convert);
    }

    private static User convert(istu.bacs.db.user.User user) {
        return new User(user.getUsername());
    }
}