package istu.bacs.web.model.user;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class Login {

    //language=regexp
    static final String USERNAME_PATTERN = "^[\\d\\w_-]{3,40}$";

    @SuppressWarnings("squid:S2068")
    //language=regexp
    static final String PASSWORD_PATTERN = "^[\\d\\w_-]{3,40}$";

    @NotNull
    @Pattern(regexp = USERNAME_PATTERN)
    String username;

    @NotNull
    @Pattern(regexp = PASSWORD_PATTERN)
    String password;
}