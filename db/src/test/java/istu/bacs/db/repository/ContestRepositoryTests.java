package istu.bacs.db.repository;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.contest.ContestRepository;
import istu.bacs.db.problem.ProblemRepository;
import istu.bacs.db.submission.SubmissionRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    List<ContestProblem> contestProblems = Arrays.asList(
            ContestProblem.builder().problem(problems.get(0)).problemIndex("A").build(),
            ContestProblem.builder().problem(problems.get(1)).problemIndex("B").build(),
            ContestProblem.builder().problem(problems.get(2)).problemIndex("C").build()
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
                .problems(contestProblems)
                .build();
        contestProblems.forEach(p -> p.setContest(contest));

        contestRepository.save(contest);
        assertThat(contest.getContestId(), is(notNullValue()));

        Optional<Contest> resultOpt = contestRepository.findById(contest.getContestId());
        assertTrue(resultOpt.isPresent());

        Contest result = resultOpt.get();
        assertThat(result.getContestId(), is(equalTo(contest.getContestId())));
        assertThat(result.getContestName(), is(equalTo(contestName)));
        assertThat(result.getStartTime(), is(equalTo(startTime)));
        assertThat(result.getFinishTime(), is(equalTo(finishTime)));
        assertEquals(contestProblems, result.getProblems());
    }
}