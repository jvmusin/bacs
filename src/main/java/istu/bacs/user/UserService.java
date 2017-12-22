package istu.bacs.user;

public interface UserService {
    User findById(int userId);
    void signUp(User user);
}