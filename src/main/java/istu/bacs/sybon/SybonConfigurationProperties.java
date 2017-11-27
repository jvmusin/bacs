package istu.bacs.sybon;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sybon")
@Getter
@Setter
@Component
public class SybonConfigurationProperties {
    private String apiKey;

    private String problemsUrl;
    private String collectionsUrl;

    private String compilersUrl;
    private String submitsUrl;
}