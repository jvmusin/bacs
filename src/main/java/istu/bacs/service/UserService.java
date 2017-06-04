package istu.bacs.service;

import istu.bacs.model.User;

public interface UserService {
	
	User findById(Integer id);
	void save(User user);
	
}