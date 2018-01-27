package istu.bacs.web.model;

import lombok.Value;

@Value
public class User {
    String username;

    public static User fromDb(istu.bacs.db.user.User user) {
        return new User(
                user.getUsername()
        );
    }
}