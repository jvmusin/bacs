package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.web.model.user.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Login login, Errors errors) {

        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors().stream()
                    .map(FieldError.class::cast)
                    .map(e -> String.format("Error on field %s (content: '%s'): %s", e.getObjectName(), e.getRejectedValue(), e.getDefaultMessage()))
                    .collect(Collectors.toList());
            return badRequest().body(errorList);
        }

        User u = User.builder()
                .username(login.getUsername())
                .password(login.getPassword())
                .build();

        try {
            userService.register(u);
            return ok().build();
        } catch (UsernameAlreadyTakenException e) {
            return status(CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}