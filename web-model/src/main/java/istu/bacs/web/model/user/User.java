package istu.bacs.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;

    public static User fromDb(istu.bacs.db.user.User user) {
        return new User(
                user.getUsername()
        );
    }
}