package istu.bacs.model;

import istu.bacs.model.type.Language;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {
	
	@Id
	private String submissionId;
	
	@ManyToOne @JoinColumn(name = "author_id")
	private User author;
	@ManyToOne @JoinColumn(name = "contest_id")
	private Contest contest;
	@ManyToOne @JoinColumn(name = "problem_id")
	private Problem problem;
	
	private LocalDateTime creationTime;
	private Language language;
	private String solution;

	@Transient
    private SubmissionResult result;

    @Data @AllArgsConstructor
    public static class SubmissionResult {
        private boolean built;
        private String buildInfo;

        private List<TestGroupResult> testGroupResults;

        public String getVerdict() {
            if (!built) return "BUILD FAILED";
            for (TestGroupResult result : testGroupResults)
                for (TestResult testResult : result.testResults)
                    if (!"OK".equals(testResult.status))
                        return testResult.status;
            return "OK";
        }

        public ResourceUsage getResourceUsage() {
            ResourceUsage ru = new ResourceUsage(0, 0);
            for (TestGroupResult result : testGroupResults) {
                for (TestResult testResult : result.testResults) {
                    ru.memoryUsedBytes = Math.max(ru.memoryUsedBytes, testResult.memoryUsedBytes);
                    ru.timeUsedMillis = Math.max(ru.timeUsedMillis, testResult.timeUsedMillis);
                }
            }
            return ru;
        }

        @Data @AllArgsConstructor
        public static class TestGroupResult {
            private Boolean executed;
            private List<TestResult> testResults;
        }

        @Data @AllArgsConstructor
        public static class TestResult {
            private String status;
            private String judgeMessage;
            private String input;
            private String output;
            private String expected;

            private Integer timeUsedMillis;
            private Integer memoryUsedBytes;
        }

        @Data @AllArgsConstructor
        public static class ResourceUsage {
            private Integer timeUsedMillis;
            private Integer memoryUsedBytes;
        }
    }
}