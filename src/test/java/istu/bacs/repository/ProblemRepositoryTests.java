package istu.bacs.repository;

import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProblemRepositoryTests {

    @Autowired
    ProblemRepository problemRepository;

    @Test
    void saveProblem() {
        String problemId = "TEST@123";
        String problemName = "Test problem";
        String statementUrl = "http://localhost/loadProblem";
        int timeLimitMillis = 1123;
        int memoryLimitBytes = 1024 * 1024 * 256;    //256MB

        Problem problem = Problem.builder()
                .problemId(problemId)
                .problemName(problemName)
                .statementUrl(statementUrl)
                .timeLimitMillis(timeLimitMillis)
                .memoryLimitBytes(memoryLimitBytes)
                .build();

        problemRepository.save(problem);

        Optional<Problem> resultOpt = problemRepository.findById(problemId);
        assertThat(resultOpt.isPresent(), is(true));

        Problem result = resultOpt.get();
        assertThat(result.getProblemId(), is(equalTo(problemId)));
        assertThat(result.getProblemName(), is(equalTo(problemName)));
        assertThat(result.getStatementUrl(), is(equalTo(statementUrl)));
        assertThat(result.getTimeLimitMillis(), is(equalTo(timeLimitMillis)));
        assertThat(result.getMemoryLimitBytes(), is(equalTo(memoryLimitBytes)));
    }
}