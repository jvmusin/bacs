package istu.bacs.web.security;

import istu.bacs.db.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class WebSecurityUserUtils {

    private WebSecurityUserUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<GrantedAuthority> getAuthorities(User user) {
        return Arrays.stream(user.getRoles())
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}