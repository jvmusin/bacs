package istu.bacs.externalapi.sybon;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sybon")
@Data
class SybonConfigurationProperties {
    private String apiKey;

    private String problemsUrl;
    private String collectionsUrl;

    private String compilersUrl;
    private String submitsUrl;
}