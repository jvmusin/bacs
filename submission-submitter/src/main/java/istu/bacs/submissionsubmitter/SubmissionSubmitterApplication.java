package istu.bacs.submissionsubmitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubmissionSubmitterApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubmissionSubmitterApplication.class, args);
    }
}