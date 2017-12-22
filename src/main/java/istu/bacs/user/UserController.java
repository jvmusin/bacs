package istu.bacs.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        userService.signUp(user);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable int userId) {
        return userService.findById(userId);
    }
}