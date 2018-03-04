package istu.bacs.web.model.user;

import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.web.model.WebModelUtils;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class FullUserInfo {

    @NotNull
    @Length(min = 3, max = 40)
    @Pattern(regexp = "^[\\d\\w_-]$")
    String username;

    @NotNull
    @Length(min = 3, max = 40)
    @Pattern(regexp = "^[\\d\\w_-]$")
    String password;

    String[] roles;

    @NotNull
    @Email
    String email;

    String firstName;
    String middleName;
    String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String birthDate;
    String registrationDate;

    public static FullUserInfo fromDb(UserPersonalDetails userPersonalDetails) {
        return new FullUserInfo(
                userPersonalDetails.getUser().getUsername(),
                null,
                userPersonalDetails.getUser().getRoles(),
                userPersonalDetails.getEmail(),
                userPersonalDetails.getFirstName(),
                userPersonalDetails.getMiddleName(),
                userPersonalDetails.getLastName(),
                WebModelUtils.formatDateTime(userPersonalDetails.getBirthDate()),
                WebModelUtils.formatDateTime(userPersonalDetails.getRegistrationDate())
        );
    }
}