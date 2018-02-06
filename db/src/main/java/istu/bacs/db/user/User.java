package istu.bacs.db.user;

import lombok.*;
import lombok.experimental.Wither;

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

    @Wither
    private String username;
    private String password;

    @Convert(converter = RolesConverter.class)
    private List<Role> roles;
}