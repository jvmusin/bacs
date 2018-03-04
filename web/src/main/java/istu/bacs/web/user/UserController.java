package istu.bacs.web.user;

import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.web.model.user.FullUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserPersonalInfoService userPersonalInfoService;

    @GetMapping("/{username}")
    public ResponseEntity<FullUserInfo> getUser(@PathVariable String username) {
        UserPersonalDetails userPersonalDetails = userPersonalInfoService.findByUsername(username);
        if (userPersonalDetails == null)
            return notFound().build();
        return ok(FullUserInfo.fromDb(userPersonalDetails));
    }
}