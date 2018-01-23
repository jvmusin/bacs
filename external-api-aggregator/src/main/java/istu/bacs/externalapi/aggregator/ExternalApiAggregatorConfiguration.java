package istu.bacs.externalapi.aggregator;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.externalapi.fake.FakeApi;
import istu.bacs.externalapi.sybon.SybonApi;
import istu.bacs.externalapi.sybon.SybonApiEndpointConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ExternalApiAggregatorConfiguration {

    @Bean
    @Profile("sybon-api")
    ExternalApi sybonApi(RestTemplateBuilder restTemplateBuilder) {
        return new SybonApi(new SybonApiEndpointConfiguration(), restTemplateBuilder);
    }

    @Bean
    @Profile("fake-api")
    ExternalApi fakeApi() {
        return new FakeApi();
    }

    @Bean
    public ExternalApiAggregator externalApiAggregator(ExternalApi[] externalApis) {
        return new ExternalApiAggregatorImpl(externalApis);
    }
}