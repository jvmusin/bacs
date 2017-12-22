package istu.bacs.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter(autoApply = true)
public class UserConverter implements AttributeConverter<User, Integer> {
    private static UserService userService;

    @Override
    public Integer convertToDatabaseColumn(User user) {
        return user.getUserId();
    }

    @Override
    public User convertToEntityAttribute(Integer userId) {
        return userService.findById(userId);
    }

    @Bean
    private CommandLineRunner initUserConverter(UserService userService) {
        return args -> UserConverter.userService = userService;
    }
}