package istu.bacs.standingsbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StandingsBuilderApplication {
    public static void main(String[] args) {
        SpringApplication.run(StandingsBuilderApplication.class, args);
    }
}