package istu.bacs.externalapi.sybonfake;

import istu.bacs.externalapi.ExternalApi;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("sybon-fake")
@AutoConfigureBefore(name = "istu.bacs.externalapi.aggregator.ExternalApiAggregatorConfiguration")
public class SybonFakeConfiguration {

    @Bean
    public ExternalApi sybonFake() {
        return new SybonFakeApi();
    }
}