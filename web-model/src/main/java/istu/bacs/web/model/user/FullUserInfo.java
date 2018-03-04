package istu.bacs.web.model.user;

import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.web.model.WebModelUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullUserInfo {

    @NotNull
    @Pattern(regexp = "^[\\d\\w_-]{3,40}$")
    private String username;

    @NotNull
    @Pattern(regexp = "^[\\d\\w_-]{3,40}$")
    private String password;

    private String[] roles;

    @Email
    private String email;

    private String firstName;
    private String middleName;
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String birthDate;
    private String registrationDate;

    public static FullUserInfo fromDb(UserPersonalDetails userPersonalDetails) {
        return new FullUserInfo(
                userPersonalDetails.getUser().getUsername(),
                null,
                userPersonalDetails.getUser().getRoles(),
                userPersonalDetails.getEmail(),
                userPersonalDetails.getFirstName(),
                userPersonalDetails.getMiddleName(),
                userPersonalDetails.getLastName(),
                WebModelUtils.formatDate(userPersonalDetails.getBirthDate()),
                WebModelUtils.formatDateTime(userPersonalDetails.getRegistrationDate())
        );
    }
}