package istu.bacs.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String username;
    private String password;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return username.equals("Musin")
                ? AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")
                : AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        User user = (User) other;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId;
    }
}