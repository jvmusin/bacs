package istu.bacs.user;

public interface UserService {
    User findById(int userId);
    User findByUsername(String username);
    void signUp(User user);
}