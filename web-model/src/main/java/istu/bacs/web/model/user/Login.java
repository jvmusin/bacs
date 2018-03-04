package istu.bacs.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    //language=regexp
    private static final String USERNAME_PATTERN = "^[\\d\\w_-]{3,40}$";

    @SuppressWarnings("squid:S2068")
    //language=regexp
    private static final String PASSWORD_PATTERN = "^[\\d\\w_-]{3,40}$";

    @NotNull
    @Pattern(regexp = USERNAME_PATTERN)
    private String username;

    @NotNull
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;
}