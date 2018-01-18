package istu.bacs.sybon;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.externalapi.ExternalApiConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(ExternalApiConfiguration.class)
public class SybonConfiguration {

    @Bean
    public ExternalApi sybonApi(RestTemplateBuilder templateBuilder) {
        return new SybonApi(new SybonApiEndpointConfiguration(), templateBuilder);
    }
}