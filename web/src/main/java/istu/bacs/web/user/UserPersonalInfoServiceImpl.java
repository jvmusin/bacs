package istu.bacs.web.user;

import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.db.user.UserPersonalDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserPersonalInfoServiceImpl implements UserPersonalInfoService {

    private final UserPersonalDetailsRepository userPersonalDetailsRepository;

    @Override
    public UserPersonalDetails findByUsername(String username) {
        return userPersonalDetailsRepository.findByUser_username(username);
    }

    @Override
    public int save(UserPersonalDetails userPersonalDetails) {
        return 0;
    }
}