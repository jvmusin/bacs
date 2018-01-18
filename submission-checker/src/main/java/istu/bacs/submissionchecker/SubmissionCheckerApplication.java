package istu.bacs.submissionchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubmissionCheckerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubmissionCheckerApplication.class, args);
    }
}