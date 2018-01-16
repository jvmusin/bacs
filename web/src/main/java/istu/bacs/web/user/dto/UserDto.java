package istu.bacs.web.user.dto;

import istu.bacs.db.user.User;
import lombok.Data;

@Data
public class UserDto {

    private int id;
    private String username;

    public UserDto(User user) {
        id = user.getUserId();
        username = user.getUsername();
    }
}