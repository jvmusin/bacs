package istu.bacs.web.user;

import istu.bacs.db.user.User;

public class EnhancedUserDetails extends org.springframework.security.core.userdetails.User {

    private final int userId;

    public EnhancedUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), UserUtils.getAuthorities(user));
        this.userId = user.getUserId();
    }

    public int getUserId() {
        return userId;
    }
}