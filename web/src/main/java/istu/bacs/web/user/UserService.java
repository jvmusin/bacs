package istu.bacs.web.user;

import istu.bacs.db.user.User;

public interface UserService {
    User findById(int userId);
    User findByUsername(String username);
    void signUp(User user);
}