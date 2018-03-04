package istu.bacs.db.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_personal_details", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class UserPersonalDetails {

    @Id
    private int userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String email;

    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDateTime birthDate;
    private LocalDateTime registrationDate;
}