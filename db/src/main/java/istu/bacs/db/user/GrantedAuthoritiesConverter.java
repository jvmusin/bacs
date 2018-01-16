package istu.bacs.db.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Component
public class GrantedAuthoritiesConverter implements AttributeConverter<List<GrantedAuthority>, String> {
    @Override
    public String convertToDatabaseColumn(List<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(","));
    }

    @Override
    public List<GrantedAuthority> convertToEntityAttribute(String authorities) {
        return commaSeparatedStringToAuthorityList(authorities);
    }
}