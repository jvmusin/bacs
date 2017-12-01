package istu.bacs.externalapi.sybon;

import istu.bacs.model.Submission.SubmissionResult;
import istu.bacs.model.Submission.SubmissionResult.TestGroupResult;
import istu.bacs.model.Submission.SubmissionResult.TestResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class SybonSubmitResultConverter implements Converter<SybonSubmitResult, SubmissionResult> {
    @Override
    public SubmissionResult convert(SybonSubmitResult submission) {
        if (submission.buildResult == null)
            return new SubmissionResult(false, null, emptyList());
        return new SubmissionResult(
                submission.buildResult.getStatus() == SybonBuildResult.Status.OK,
                submission.buildResult.getOutput(),
                submission.getTestResults().stream()
                        .map(this::convertTestGroupResult)
                        .collect(toList())
        );
    }

    private TestGroupResult convertTestGroupResult(SybonTestGroupResult result) {
        return new TestGroupResult(
                result.getExecuted(),
                result.getTestResults().stream()
                        .map(this::convertTestResult)
                        .collect(toList())
        );
    }

    private TestResult convertTestResult(SybonTestResult res) {
        return new TestResult(
                res.getStatus().name(),
                res.getJudgeMessage(),
                res.getInput(),
                res.getActualResult(),
                res.getExpectedResult(),
                res.getResourceUsage().getTimeUsageMillis(),
                res.getResourceUsage().getMemoryUsageBytes()
        );
    }
}