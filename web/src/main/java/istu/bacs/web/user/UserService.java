package istu.bacs.web.user;

import istu.bacs.db.user.User;

public interface UserService {
    User findById(int userId);
    void signUp(User user);
}