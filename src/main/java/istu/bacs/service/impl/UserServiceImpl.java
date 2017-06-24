package istu.bacs.service.impl;

import istu.bacs.model.User;
import istu.bacs.repository.UserRepository;
import istu.bacs.service.UserService;
import istu.bacs.service.UsernameAlreadyInUseException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public void register(User user) throws UsernameAlreadyInUseException {
		if (userRepository.findByUsername(user.getUsername()) != null)
			throw new UsernameAlreadyInUseException(user.getUsername());
		userRepository.saveAndFlush(user);
	}
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}
}