package istu.bacs.submission;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestGroupResult {
    private Boolean executed;
    private List<TestResult> testResults;
}
