package istu.bacs.contest.dto;

import istu.bacs.user.User;
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