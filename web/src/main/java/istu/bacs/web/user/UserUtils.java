package istu.bacs.web.user;

import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class UserUtils {

    public static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(Role::name)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}