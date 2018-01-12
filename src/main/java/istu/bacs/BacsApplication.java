package istu.bacs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class BacsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BacsApplication.class, args);
    }
}