package istu.bacs.service.impl;

import istu.bacs.model.User;
import istu.bacs.repository.UserRepository;
import istu.bacs.service.UserService;
import istu.bacs.service.UsernameAlreadyInUseException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	public void register(User user) throws UsernameAlreadyInUseException {
		if (userRepository.findByUsername(user.getUsername()) != null)
			throw new UsernameAlreadyInUseException(user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}
}