package istu.bacs.externalapi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalApiConfiguration {

    @Bean
    @ConditionalOnBean(ExternalApi.class)
    public ExternalApiAggregator externalApiAggregator(ExternalApi[] externalApis) {
        return new ExternalApiAggregatorImpl(externalApis);
    }

    @Bean
    @ConditionalOnMissingBean(ExternalApi.class)
    public ExternalApiAggregator externalApiAggregatorEmpty() {
        return new ExternalApiAggregatorImpl(new ExternalApi[0]);
    }
}