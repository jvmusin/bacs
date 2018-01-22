package istu.bacs.db.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String username;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @Convert(converter = RolesConverter.class)
    private List<Role> roles;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        User user = (User) other;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return userId;
    }
}