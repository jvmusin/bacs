package istu.bacs.externalapi.fake;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.submission.Verdict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static istu.bacs.db.submission.Verdict.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("FakeApi")
class FakeApiTests {

    private static final Problem problem = Problem.builder().memoryLimitBytes(100).timeLimitMillis(100).build();
    private static final ContestProblem contestProblem = ContestProblem.builder().problem(problem).build();
    private FakeApi fakeApi = new FakeApi();

    private static Submission submission(Object solution) {
        return Submission.builder()
                .solution(solution.toString())
                .contestProblem(contestProblem)
                .result(SubmissionResult.builder().build())
                .build();
    }

    @Test
    @DisplayName("Return 'FAKE' on getApiName()")
    void returnFakeApiName() {
        assertEquals(fakeApi.getApiName(), "FAKE");
    }

    @Nested
    @DisplayName("When checking results should")
    class WhenCheckingShould {

        @Test
        @DisplayName("Set verdict by parsing solution as a verdict name")
        void setVerdictByParsingSolutionAsAVerdictName() {
            for (Verdict verdict : Verdict.values()) {
                Submission submission = submission(verdict);

                fakeApi.checkSubmissionResult(submission);

                assertEquals(verdict, submission.getVerdict());
            }
        }

        @Test
        @DisplayName("Set verdict to compile error when solution doesn't match any verdict")
        void setVerdictToCompileError_whenSolutionDoesntMatchAnyVerdict() {
            Submission submission = submission("hello !!!111!!1!!1!");

            fakeApi.checkSubmissionResult(submission);

            SubmissionResult result = submission.getResult();
            assertEquals(COMPILE_ERROR, result.getVerdict());
            assertEquals(result.getTestsPassed(), null);
            assertEquals(result.getTimeUsedMillis(), null);
            assertEquals(result.getMemoryUsedBytes(), null);

            assertNotNull(result.getBuildInfo());
            assertFalse(result.getBuildInfo().isEmpty());
        }

        @Test
        @DisplayName("Ignore solution case")
        void checkVerdictsIgnoringCase() {
            Submission submission = submission("abNORMAl_exiT");

            fakeApi.checkSubmissionResult(submission);

            assertEquals(submission.getVerdict(), ABNORMAL_EXIT);
        }

        @Test
        @DisplayName("Set null 'testsPassed' when ACCEPTED")
        void setNullTestsPassed_whenAccepted() {
            Submission submission = submission(ACCEPTED);

            assertNull(submission.getResult().getTestsPassed());
        }

        @Test
        @DisplayName("Set correct time and memory used when verdict accepted")
        void setCorrectTimeAndMemoryUsed_whenVerdictAccepted() {
            Submission submission = submission(ACCEPTED);

            fakeApi.checkSubmissionResult(submission);

            assertTrue(submission.getResult().getTimeUsedMillis() <= problem.getTimeLimitMillis(), submission::toString);
            assertTrue(submission.getResult().getMemoryUsedBytes() <= problem.getMemoryLimitBytes(), submission::toString);
        }

        @DisplayName("Overflow only time limit when TIME_LIMIT_EXCEEDED passed")
        @RepeatedTest(10)
        void overflowOnlyTimeLimit_whenTimeLimitVerdict() {
            Submission submission = submission(TIME_LIMIT_EXCEEDED);

            fakeApi.checkSubmissionResult(submission);

            SubmissionResult result = submission.getResult();
            assertNotNull(result.getTestsPassed());
            assertTrue(result.getTimeUsedMillis() > problem.getTimeLimitMillis(), submission::toString);
            assertTrue(result.getMemoryUsedBytes() <= problem.getMemoryLimitBytes(), submission::toString);
            assertNull(result.getBuildInfo());
        }

        @DisplayName("Overflow only memory limit when MEMORY_LIMIT_EXCEEDED passed")
        @RepeatedTest(10)
        void overflowOnlyMemoryLimit_whenMemoryLimitVerdict() {
            Submission submission = submission(MEMORY_LIMIT_EXCEEDED);

            fakeApi.checkSubmissionResult(submission);

            SubmissionResult result = submission.getResult();
            assertNotNull(result.getTestsPassed());
            assertTrue(result.getTimeUsedMillis() <= problem.getTimeLimitMillis(), submission::toString);
            assertTrue(result.getMemoryUsedBytes() > problem.getMemoryLimitBytes(), submission::toString);
            assertNull(result.getBuildInfo());
        }
    }

    @Nested
    @DisplayName("When fetching problems should")
    class WhenFetchingProblemsShould {

        @Test
        @DisplayName("Return not empty set of problems")
        void returnNotEmptySetOfProblems() {
            assertFalse(fakeApi.getAllProblems().isEmpty());
        }

        @Test
        @DisplayName("Return fully specified problem details")
        void returnFullySpecifiedProblemDetails() {
            for (Problem problem : fakeApi.getAllProblems()) {
                assertNotNull(problem.getProblemId());
                assertTrue(problem.getProblemId().startsWith("FAKE@"));
                assertFalse(problem.getProblemId().replace("FAKE@", "").isEmpty());

                assertFalse(problem.getName().isEmpty());
                assertFalse(problem.getStatementUrl().isEmpty());

                assertTrue(problem.getTimeLimitMillis() > 0);
                assertTrue(problem.getMemoryLimitBytes() > 0);
            }
        }
    }
}