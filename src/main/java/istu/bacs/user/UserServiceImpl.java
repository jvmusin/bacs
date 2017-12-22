package istu.bacs.user;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(key = "#userId")
    public User findById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    @CacheEvict(key = "#user.userId")
    public void signUp(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new RuntimeException("Username is already taken: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}