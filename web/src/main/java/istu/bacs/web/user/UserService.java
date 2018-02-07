package istu.bacs.web.user;

import istu.bacs.db.user.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findByUsername(String username);
    void register(User user);
}