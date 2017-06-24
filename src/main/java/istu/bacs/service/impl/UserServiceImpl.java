package istu.bacs.service.impl;

import istu.bacs.model.User;
import istu.bacs.repository.UserRepository;
import istu.bacs.service.UserService;
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
	public void save(User user) {
		userRepository.save(user);
	}
	
}