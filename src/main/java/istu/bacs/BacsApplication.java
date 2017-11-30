package istu.bacs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class BacsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BacsApplication.class, args);
	}
}