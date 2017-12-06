package istu.bacs.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Data
@Entity
public class User implements UserDetails, Comparable<User> {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	private String username;
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return username.equals("Musin")
                ? AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")
                : AuthorityUtils.createAuthorityList("ROLE_USER");
    }
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

    @Override
    public int compareTo(User other) {
        return userId.compareTo(other.userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null &&
                getClass() == o.getClass() &&
                userId.equals(((User) o).userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}