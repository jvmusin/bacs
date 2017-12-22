package istu.bacs.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Component
public class GrantedAuthoritiesConverter implements AttributeConverter<List<GrantedAuthority>, String> {
    @Override
    public String convertToDatabaseColumn(List<GrantedAuthority> attribute) {
        return attribute.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(","));
    }

    @Override
    public List<GrantedAuthority> convertToEntityAttribute(String authorities) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }
}