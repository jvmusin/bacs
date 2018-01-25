package istu.bacs.db.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
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
}