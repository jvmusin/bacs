package istu.bacs.repository;

import istu.bacs.contest.Contest;
import istu.bacs.contest.ContestRepository;
import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemRepository;
import istu.bacs.submission.Language;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionRepository;
import istu.bacs.user.User;
import istu.bacs.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static istu.bacs.submission.Language.Python3;
import static istu.bacs.submission.SubmissionResult.withVerdict;
import static istu.bacs.submission.Verdict.PENDING;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(PER_CLASS)
class SubmissionRepositoryTest {

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ContestRepository contestRepository;

    User author = User.builder()
            .username("Me")
            .password("pass")
            .authorities(AuthorityUtils.createAuthorityList("ROLE_USER"))
            .build();
    List<Problem> problems = Arrays.asList(
            Problem.builder().problemId("ProA").problemName("Problem A").statementUrl("urlA").timeLimitMillis(1010).memoryLimitBytes(1000).build(),
            Problem.builder().problemId("ProB").problemName("Problem B").statementUrl("urlB").timeLimitMillis(1020).memoryLimitBytes(2000).build(),
            Problem.builder().problemId("ProC").problemName("Problem C").statementUrl("urlC").timeLimitMillis(1030).memoryLimitBytes(3000).build()
    );
    Contest contest = Contest.builder()
            .contestName("Test Contest")
            .startTime(LocalDateTime.now())
            .finishTime(LocalDateTime.now().plusHours(5))
            .problems(problems)
            .build();

    @BeforeAll
    void init() {
        userRepository.save(author);
        problemRepository.saveAll(problems);
        contestRepository.save(contest);
    }

    @Test
    void saveSubmission() {
        Problem problem = problems.get(1);
        boolean pretestsOnly = false;
        LocalDateTime created = contest.getStartTime().plusMinutes(5);
        Language language = Python3;
        String solution = "from python import solution";
        String externalSubmissionId = "EXT";

        Submission submission = Submission.builder()
                .author(author)
                .contest(contest)
                .problem(problem)
                .pretestsOnly(pretestsOnly)
                .created(created)
                .language(language)
                .solution(solution)
                .externalSubmissionId(externalSubmissionId)
                .build();
        submission.setResult(withVerdict(submission, PENDING));

        submissionRepository.save(submission);
        assertThat(submission.getSubmissionId(), is(notNullValue()));

        Optional<Submission> resultOpt = submissionRepository.findById(submission.getSubmissionId());
        assertThat(resultOpt.isPresent(), is(true));

        Submission result = resultOpt.get();
        assertThat(result.getSubmissionId(), is(equalTo(submission.getSubmissionId())));
        assertThat(result.getAuthor(), is(equalTo(author)));
        assertThat(result.getContest(), is(equalTo(contest)));
        assertThat(result.getProblem(), is(equalTo(problem)));
        assertThat(result.isPretestsOnly(), is(equalTo(pretestsOnly)));
        assertThat(result.getCreated(), is(equalTo(created)));
        assertThat(result.getLanguage(), is(equalTo(language)));
        assertThat(result.getSolution(), is(equalTo(solution)));
        assertThat(result.getExternalSubmissionId(), is(equalTo(externalSubmissionId)));
    }
}