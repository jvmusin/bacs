package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.web.model.WebModelUtils;
import istu.bacs.web.model.user.FullUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody FullUserInfo user, Errors errors) {

        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors().stream()
                    .map(FieldError.class::cast)
                    .map(e -> String.format("Найдена ошибка в поле %s (текущее значение: '%s'): %s",
                            e.getField(), e.getRejectedValue(), e.getDefaultMessage()))
                    .collect(Collectors.toList());
            return badRequest().body(errorList);
        }

        User u = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        UserPersonalDetails ud = UserPersonalDetails.builder()
                .user(u)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthDate(WebModelUtils.parseDateTime(user.getBirthDate()))
                .registrationDate(LocalDateTime.now())
                .build();

        try {
            userService.register(ud);
            return ok().build();
        } catch (UsernameAlreadyTakenException e) {
            return status(CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return badRequest().body(e.getMessage());
        }
    }
}