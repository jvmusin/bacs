package istu.bacs.repository;

import istu.bacs.contest.Contest;
import istu.bacs.contest.ContestRepository;
import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemRepository;
import istu.bacs.submission.SubmissionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(PER_CLASS)
class ContestRepositoryTests {

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ContestRepository contestRepository;

    List<Problem> problems = Arrays.asList(
            Problem.builder().problemId("ProA").problemName("Problem A").statementUrl("urlA").timeLimitMillis(1010).memoryLimitBytes(1000).build(),
            Problem.builder().problemId("ProB").problemName("Problem B").statementUrl("urlB").timeLimitMillis(1020).memoryLimitBytes(2000).build(),
            Problem.builder().problemId("ProC").problemName("Problem C").statementUrl("urlC").timeLimitMillis(1030).memoryLimitBytes(3000).build()
    );

    @BeforeAll
    void init() {
        problemRepository.saveAll(problems);
    }

    @Test
    void saveContest() {
        String contestName = "Test Contest";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime finishTime = startTime.plusHours(5);

        Contest contest = Contest.builder()
                .contestName(contestName)
                .startTime(startTime)
                .finishTime(finishTime)
                .problems(problems)
                .build();

        contestRepository.save(contest);
        assertThat(contest.getContestId(), is(notNullValue()));

        Optional<Contest> resultOpt = contestRepository.findById(contest.getContestId());
        assertThat(resultOpt.isPresent(), is(true));

        Contest result = resultOpt.get();
        assertThat(result.getContestId(), is(equalTo(contest.getContestId())));
        assertThat(result.getContestName(), is(equalTo(contestName)));
        assertThat(result.getStartTime(), is(equalTo(startTime)));
        assertThat(result.getFinishTime(), is(equalTo(finishTime)));
        assertThat(result.getProblems(), equalTo(problems));
    }
}