package istu.bacs.externalapi.sybon;

import istu.bacs.model.Submission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Component
public class SybonSubmitResultConverter implements Converter<SybonSubmitResult, Submission.SubmissionResult> {
    @Override
    public Submission.SubmissionResult convert(SybonSubmitResult submission) {
        return new Submission.SubmissionResult(
                submission.buildResult.getStatus() == SybonBuildResult.Status.OK,
                submission.buildResult.getOutput(),
                submission.getTestResults().stream()
                        .map(this::convertTestGroupResult)
                        .collect(toList())
        );
    }

    private Submission.SubmissionResult.TestGroupResult convertTestGroupResult(SybonTestGroupResult result) {
        return new Submission.SubmissionResult.TestGroupResult(
                result.getExecuted(),
                result.getTestResults().stream()
                        .map(this::convertTestResult)
                        .collect(toList())
        );
    }

    private Submission.SubmissionResult.TestResult convertTestResult(SybonTestResult res) {
        return new Submission.SubmissionResult.TestResult(
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