package istu.bacs.web.user;

import istu.bacs.db.user.UserPersonalDetails;

public interface UserPersonalInfoService {

    UserPersonalDetails findByUsername(String username);
    int save(UserPersonalDetails userPersonalDetails);

}