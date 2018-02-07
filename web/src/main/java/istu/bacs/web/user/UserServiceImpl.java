package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static istu.bacs.db.user.Role.ROLE_USER;
import static java.util.Collections.singletonList;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.debug("Registration failed: Username is already taken: '{}':'{}'", user.getUsername(), user.getPassword());
            throw new UsernameAlreadyTakenException(user.getUsername());
        }

        if (isEmpty(user.getUsername())) {
            String message = "Registration failed: Username shouldn't be empty";
            log.debug(message);
            throw new IllegalArgumentException(message);
        }

        if (isEmpty(user.getPassword())) {
            String message = "Registration failed: Password shouldn't be empty";
            log.debug(message);
            throw new IllegalArgumentException(message);
        }

        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(singletonList(ROLE_USER));
        userRepository.save(user);
        log.debug("User successfully registered: {}:'{}':'{}'", user.getUserId(), user.getUsername(), pass);
    }
}