package istu.bacs.externalapi.sybon;

import istu.bacs.externalapi.ExternalApi;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AutoConfigureBefore(name = "istu.bacs.externalapi.aggregator.ExternalApiAggregatorConfiguration")
@Profile("sybon")
public class SybonConfiguration {

    @Bean
    public ExternalApi sybonApi(RestTemplateBuilder templateBuilder) {
        return new SybonApi(new SybonApiEndpointConfiguration(), templateBuilder);
    }
}