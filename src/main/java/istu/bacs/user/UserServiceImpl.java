package istu.bacs.user;

import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void signUp(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.info(format("Registration failed: Username is already taken: '%s':'%s'", user.getUsername(), user.getPassword()));
            throw new UsernameAlreadyTakenException(user.getUsername());
        }

        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
        user.setAuthorities(createAuthorityList("ROLE_USER"));
        userRepository.save(user);
        log.info(format("User successfully registered: %d:'%s':'%s'", user.getUserId(), user.getUsername(), pass));
    }
}