package istu.bacs.web.security;

import istu.bacs.db.user.User;

public class EnhancedUserDetails extends org.springframework.security.core.userdetails.User {

    private final int userId;

    public EnhancedUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), WebSecurityUserUtils.getAuthorities(user));
        this.userId = user.getUserId();
    }

    public int getUserId() {
        return userId;
    }
}