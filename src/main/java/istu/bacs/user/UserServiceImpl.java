package istu.bacs.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
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
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UsernameAlreadyTakenException(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(createAuthorityList("ROLE_USER"));
        userRepository.save(user);
    }
}