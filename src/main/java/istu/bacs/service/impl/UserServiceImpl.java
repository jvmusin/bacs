package istu.bacs.service.impl;

import istu.bacs.model.User;
import istu.bacs.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
	
	private final Map<Integer, User> userById = new HashMap<>();
	
	@Override
	public User findById(Integer id) {
		return userById.get(id);
	}
	
	@Override
	public void save(User user) {
		userById.put(user.getUserId(), user);
	}
	
}