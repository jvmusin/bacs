package istu.bacs.web.user;

import istu.bacs.db.user.User;
import istu.bacs.db.user.UserPersonalDetails;

public interface UserService {
    User findByUsername(String username);

    void register(UserPersonalDetails userDetails);
}