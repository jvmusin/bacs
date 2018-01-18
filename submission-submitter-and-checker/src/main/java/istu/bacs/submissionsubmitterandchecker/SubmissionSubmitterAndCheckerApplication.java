package istu.bacs.submissionsubmitterandchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubmissionSubmitterAndCheckerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubmissionSubmitterAndCheckerApplication.class, args);
    }
}