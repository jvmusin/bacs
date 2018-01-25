package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.web.user.dto.NewUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"sign-up", "users"})
    public ResponseEntity<?> signUp(@RequestBody NewUserDto user) {
        User u = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        try {
            userService.signUp(u);
            return ok().build();
        } catch (UsernameAlreadyTakenException e) {
            return status(CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return status(FORBIDDEN).body(e.getMessage());
        }
    }
}