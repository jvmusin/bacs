package istu.bacs.web.rest.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserRepository;
import istu.bacs.web.model.Login;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import static istu.bacs.db.user.Role.ROLE_USER;
import static java.util.Collections.singletonList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findByUsername(Mono<String> username) {
        return username.map(userRepository::findByUsername);
    }

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll());
    }

    @Override
    public Mono<User> update(Mono<String> username, Mono<istu.bacs.web.model.User> user) {
        return username.zipWith(user)
                .map(t -> Tuples.of(userRepository.findByUsername(t.getT1()), t.getT2()))
                .map(t -> new User(
                        t.getT1().getUserId(),
                        t.getT2().getUsername(),
                        t.getT1().getPassword(),
                        t.getT1().getRoles()))
                .map(userRepository::save);
    }

    @Override
    public Mono<User>update(String username, istu.bacs.web.model.User user) {
        return update(Mono.just(username), Mono.just(user));
    }

    @Override
    public Mono<User> create(Mono<Login> user) {
        return user
                .map(u -> new User(null, u.getUsername(), u.getPassword(), singletonList(ROLE_USER)))
                .map(userRepository::save);
    }
}