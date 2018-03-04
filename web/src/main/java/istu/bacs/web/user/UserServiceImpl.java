package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.db.user.UserPersonalDetailsRepository;
import istu.bacs.db.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.isEmpty;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPersonalDetailsRepository userPersonalDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(UserPersonalDetails userPersonalDetails) {
        User user = userPersonalDetails.getUser();

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
        user.setRoles(new String[]{"ROLE_USER"});
        userRepository.save(user);
        userPersonalDetails.setUserId(user.getUserId());
        userPersonalDetailsRepository.save(userPersonalDetails);
        log.debug("User successfully registered: {}:'{}':'{}'", user.getUserId(), user.getUsername(), pass);
    }
}