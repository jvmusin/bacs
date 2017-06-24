package istu.bacs.service;

import istu.bacs.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	User findById(Integer id);
	void save(User user);
	
}