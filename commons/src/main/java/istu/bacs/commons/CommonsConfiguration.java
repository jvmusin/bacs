package istu.bacs.commons;

import istu.bacs.commons.initializer.PlatformInitializer;
import istu.bacs.commons.initializer.PlatformUnitInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonsConfiguration {

    @Bean
    @ConditionalOnBean(PlatformUnitInitializer.class)
    public PlatformInitializer platformInitializer(PlatformUnitInitializer[] initializers) {
        return new PlatformInitializer(initializers);
    }
}