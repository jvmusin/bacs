package istu.bacs.web.user;

import istu.bacs.web.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(User::fromDb)
                .collect(toList());
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return User.fromDb(userService.findByUsername(username));
    }
}