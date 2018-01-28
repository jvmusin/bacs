package istu.bacs.web.security;

import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static istu.bacs.db.user.Role.ROLE_GUEST;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    public static final JWTAuthenticationToken ANONYMOUS = new JWTAuthenticationToken(
            -1,
            "ANONYMOUS",
            singletonList(ROLE_GUEST)
    );

    private final int userId;
    private final String username;
    private final List<Role> roles;

    public JWTAuthenticationToken(int userId, String username, List<Role> roles) {
        super(roles.stream().map(Role::name).map(SimpleGrantedAuthority::new).collect(toList()));
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return new User(userId, username, null, roles);
    }
}