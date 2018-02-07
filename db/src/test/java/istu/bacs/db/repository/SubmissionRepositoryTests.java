//package istu.bacs.db.repository;
//
//import istu.bacs.db.contest.Contest;
//import istu.bacs.db.contest.ContestProblem;
//import istu.bacs.db.contest.ContestProblemRepository;
//import istu.bacs.db.contest.ContestRepository;
//import istu.bacs.db.problem.Problem;
//import istu.bacs.db.problem.ProblemRepository;
//import istu.bacs.db.submission.Language;
//import istu.bacs.db.submission.Submission;
//import istu.bacs.db.submission.SubmissionRepository;
//import istu.bacs.db.submission.SubmissionResult;
//import istu.bacs.db.user.Role;
//import istu.bacs.db.user.User;
//import istu.bacs.db.user.UserRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static istu.bacs.db.submission.Language.Python3;
//import static istu.bacs.db.submission.Verdict.PENDING;
//import static istu.bacs.db.user.Role.ROLE_USER;
//import static java.util.Collections.singletonList;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@TestInstance(PER_CLASS)
//class SubmissionRepositoryTests {
//
//    @Autowired
//    SubmissionRepository submissionRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ProblemRepository problemRepository;
//
//    @Autowired
//    ContestProblemRepository contestProblemRepository;
//
//    @Autowired
//    ContestRepository contestRepository;
//
//    User author = User.builder()
//            .username("Me")
//            .password("pass")
//            .roles(singletonList(ROLE_USER))
//            .build();
//
//    Contest contest = Contest.builder()
//            .name("Test Contest")
//            .startTime(LocalDateTime.now())
//            .finishTime(LocalDateTime.now().plusHours(5))
//            .build();
//
//    List<Problem> problems = Arrays.asList(
//            Problem.builder().problemId("TEST@ProA").name("Problem A").statementUrl("urlA").timeLimitMillis(1010).memoryLimitBytes(1000).build(),
//            Problem.builder().problemId("TEST@ProB").name("Problem B").statementUrl("urlB").timeLimitMillis(1020).memoryLimitBytes(2000).build(),
//            Problem.builder().problemId("TEST@ProC").name("Problem C").statementUrl("urlC").timeLimitMillis(1030).memoryLimitBytes(3000).build()
//    );
//
//    List<ContestProblem> contestProblems = Arrays.asList(
//            ContestProblem.builder().contest(contest).problem(problems.get(0)).problemIndex("A").build(),
//            ContestProblem.builder().contest(contest).problem(problems.get(1)).problemIndex("B").build(),
//            ContestProblem.builder().contest(contest).problem(problems.get(2)).problemIndex("C").build()
//    );
//
//    @BeforeAll
//    void init() {
//        userRepository.save(author);
//        problemRepository.saveAll(problems);
//
//        contest.setProblems(contestProblems);
//        contestRepository.save(contest);
//    }
//
//    @Test
//    void saveSubmission() {
//        ContestProblem problem = contest.getProblems().get(1);
//        boolean pretestsOnly = false;
//        LocalDateTime created = contest.getStartTime().plusMinutes(5);
//        Language language = Python3;
//        String solution = "from python import solution";
//        int externalSubmissionId = 666;
//
//        Submission submission = Submission.builder()
//                .author(author)
//                .contestProblem(problem)
//                .pretestsOnly(pretestsOnly)
//                .created(created)
//                .language(language)
//                .solution(solution)
//                .externalSubmissionId(externalSubmissionId)
//                .result(SubmissionResult.builder().verdict(PENDING).build())
//                .build();
//
//        submissionRepository.save(submission);
//        assertThat(submission.getSubmissionId(), is(notNullValue()));
//
//        Optional<Submission> resultOpt = submissionRepository.findById(submission.getSubmissionId());
//        assertTrue(resultOpt.isPresent());
//
//        Submission result = resultOpt.get();
//        assertThat(result.getSubmissionId(), is(equalTo(submission.getSubmissionId())));
//        assertThat(result.getAuthor(), is(equalTo(author)));
//        assertThat(result.getContest(), is(equalTo(contest)));
//        assertThat(result.getContestProblem(), is(equalTo(problem)));
//        assertThat(result.isPretestsOnly(), is(equalTo(pretestsOnly)));
//        assertThat(result.getCreated(), is(equalTo(created)));
//        assertThat(result.getLanguage(), is(equalTo(language)));
//        assertThat(result.getSolution(), is(equalTo(solution)));
//        assertThat(result.getExternalSubmissionId(), is(equalTo(externalSubmissionId)));
//    }
//}