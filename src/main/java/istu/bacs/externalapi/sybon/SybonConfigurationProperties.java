package istu.bacs.externalapi.sybon;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "sybon")
@Data
public class SybonConfigurationProperties {
    private String apiKey;

    private String problemsUrl;
    private String collectionsUrl;

    private String compilersUrl;
    private String submitsUrl;
}