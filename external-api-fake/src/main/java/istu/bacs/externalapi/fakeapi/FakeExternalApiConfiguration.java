package istu.bacs.externalapi.fakeapi;

import istu.bacs.externalapi.ExternalApi;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("fake-api")
@AutoConfigureBefore(name = "istu.bacs.externalapi.aggregator.ExternalApiAggregatorConfiguration")
public class FakeExternalApiConfiguration {

    @Bean
    public ExternalApi fakeApi() {
        return new FakeExternalApi();
    }
}