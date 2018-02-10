package istu.bacs.db.user;

import lombok.*;
import lombok.experimental.Wither;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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
    @Wither
    private Integer userId;

    @Wither
    private String username;
    private String password;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] roles;
}