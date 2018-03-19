package istu.bacs.web.helper;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.web.model.user.Login;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Users {

    public static final UserPersonalDetails admin = build("admin", true);
    public static final Login adminLogin = new Login(admin.getUser().getUsername(), admin.getUser().getPassword());

    public static final UserPersonalDetails regular = build("regular", false);
    public static final Login regularLogin = new Login(regular.getUser().getUsername(), regular.getUser().getPassword());

    private static UserPersonalDetails build(String username, boolean admin) {
        return UserPersonalDetails.builder()
                .user(User.builder().username(username).password(username + "_pass").roles(admin
                        ? new String[]{"ROLE_USER", "ROLE_ADMIN"}
                        : new String[]{"ROLE_USER"}
                ).build())
                .firstName("First_" + username)
                .middleName("Middle_" + username)
                .lastName("Last_" + username)
                .email(username + "@rustam.molodec")
                .birthDate(LocalDate.now().minusYears(20))
                .registrationDate(LocalDateTime.now().minusDays(5))
                .build();
    }
}
