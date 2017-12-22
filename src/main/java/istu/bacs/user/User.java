package istu.bacs.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String username;
    private String password;

    @Convert(converter = GrantedAuthoritiesConverter.class)
    private List<GrantedAuthority> authorities;

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