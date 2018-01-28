package istu.bacs.web.security;

import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EnhancedUserDetails extends org.springframework.security.core.userdetails.User {

    private final int userId;

    public EnhancedUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), WebSecurityUserUtils.getAuthorities(user));
        this.userId = user.getUserId();
    }

    public int getUserId() {
        return userId;
    }

    public List<Role> getRoles() {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Role::valueOf)
                .collect(toList());
    }
}