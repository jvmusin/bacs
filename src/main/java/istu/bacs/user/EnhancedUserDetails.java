package istu.bacs.user;

public class EnhancedUserDetails extends org.springframework.security.core.userdetails.User {

    private final int userId;

    public EnhancedUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.userId = user.getUserId();
    }

    public int getUserId() {
        return userId;
    }
}