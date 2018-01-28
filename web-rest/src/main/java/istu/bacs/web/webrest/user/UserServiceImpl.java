package istu.bacs.web.webrest.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(userRepository.findByUsername(username));
    }

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll());
    }

    @Override
    public Mono<User> update(String username, istu.bacs.web.model.User user) {
        return Mono.justOrEmpty(userRepository.findByUsername(username))
                .map(u -> new User(u.getUserId(), user.getUsername(), u.getPassword(), u.getRoles()))
                .map(userRepository::save);
    }
}

