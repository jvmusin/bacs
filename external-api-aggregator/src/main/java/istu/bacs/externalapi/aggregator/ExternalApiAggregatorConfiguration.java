package istu.bacs.externalapi.aggregator;

import istu.bacs.externalapi.ExternalApi;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ExternalApiAggregatorConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public ExternalApiAggregator externalApiAggregator() {
        Map<String, ExternalApi> beans = applicationContext.getBeansOfType(ExternalApi.class);
        ExternalApi[] externalApis = beans.values().toArray(new ExternalApi[0]);
        return new ExternalApiAggregatorImpl(externalApis);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}