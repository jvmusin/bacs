package istu.bacs;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemService;
import istu.bacs.standings.StandingsService;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.List;

import static istu.bacs.submission.Verdict.PENDING;
import static java.util.stream.Collectors.toList;

@EnableConfigurationProperties
@EnableScheduling
@SpringBootApplication
public class BacsApplication {

//    private final ExternalApiAggregator externalApi;
//    private final ProblemService problemService;
//    private final StandingsService standingsService;
//    private final SubmissionService submissionService;
//
//    public BacsApplication(ExternalApiAggregator externalApi, ProblemService problemService, StandingsService standingsService, SubmissionService submissionService) {
//        this.externalApi = externalApi;
//        this.problemService = problemService;
//        this.standingsService = standingsService;
//        this.submissionService = submissionService;
//    }

    public static void main(String[] args) {
        SpringApplication.run(BacsApplication.class, args);
    }

    @PostConstruct
    public void init() {
//        fetchProblems();
//        initStandings();
    }

//    private void fetchProblems() {
//        List<Problem> problems = externalApi.getAllProblems();
//        problemService.saveAll(problems);
//    }
//
//    private void initStandings() {
//        List<Submission> submissions = submissionService.findAll().stream()
//                .filter(sub -> sub.getVerdict() != PENDING).collect(toList());
//        standingsService.update(submissions);
//    }
}