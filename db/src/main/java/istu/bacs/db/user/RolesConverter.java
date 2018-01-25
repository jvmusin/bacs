package istu.bacs.db.user;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class RolesConverter implements AttributeConverter<List<Role>, String> {
    @Override
    public String convertToDatabaseColumn(List<Role> authorities) {
        return authorities.stream()
                .map(Role::name)
                .collect(joining(","));
    }

    @Override
    public List<Role> convertToEntityAttribute(String authorities) {
        return Arrays.stream(authorities.split(","))
                .map(Role::valueOf)
                .collect(toList());
    }
}