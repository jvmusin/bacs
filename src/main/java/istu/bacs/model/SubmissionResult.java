package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SubmissionResult {
    private boolean built;
    private String buildInfo;

    private List<TestGroupResult> testGroupResults;

    public String getVerdict() {
        if (!built) return "BUILD FAILED";
        for (TestGroupResult result : testGroupResults)
            for (TestResult testResult : result.getTestResults())
                if (!"OK".equals(testResult.getStatus()))
                    return testResult.getStatus();
        return "OK";
    }

    public ResourceUsage getResourceUsage() {
        ResourceUsage max = new ResourceUsage(0, 0);
        for (TestGroupResult result : testGroupResults) {
            for (TestResult testResult : result.getTestResults()) {
                if (testResult.getMemoryUsedBytes() > max.getMemoryUsedBytes())
                    max.setMemoryUsedBytes(testResult.getMemoryUsedBytes());
                if (testResult.getTimeUsedMillis() > max.getTimeUsedMillis())
                    max.setTimeUsedMillis(testResult.getTimeUsedMillis());
            }
        }
        return max;
    }
}


