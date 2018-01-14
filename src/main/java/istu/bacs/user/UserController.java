package istu.bacs.user;

import istu.bacs.user.dto.NewUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody NewUserDto user) {
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());

        try {
            userService.signUp(u);
            return ResponseEntity.ok().build();
        } catch (UsernameAlreadyTakenException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable int userId) {
        return userService.findById(userId);
    }
}