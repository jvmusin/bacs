package istu.bacs.user;

import istu.bacs.user.User;
import istu.bacs.user.UsernameAlreadyInUseException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	User findById(int userId);
	void register(User user) throws UsernameAlreadyInUseException;
	
}