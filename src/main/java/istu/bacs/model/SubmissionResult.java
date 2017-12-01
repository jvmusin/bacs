package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.transform.OutputKeys;
import java.util.List;

@Data
@AllArgsConstructor
public class SubmissionResult {
    private boolean built;
    private String buildInfo;

    private Verdict verdict;
    private Integer firstFailedTest;
    private int timeUsedMillis;
    private int memoryUsedBytes;

    private List<TestGroupResult> testGroupResults;
}