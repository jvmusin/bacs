package istu.bacs.db.user;

import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Wither
    private String username;
    private String password;

    @Convert(converter = RolesConverter.class)
    private List<Role> roles;
}