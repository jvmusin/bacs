package istu.bacs.externalapi.sybon;

import istu.bacs.model.SubmissionResult;
import istu.bacs.model.TestGroupResult;
import istu.bacs.model.TestResult;
import istu.bacs.model.Verdict;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class SybonSubmitResultConverter implements Converter<SybonSubmitResult, SubmissionResult> {

    private final SybonTestResultStatusConverter testResultStatusConverter;

    SybonSubmitResultConverter(SybonTestResultStatusConverter testResultStatusConverter) {
        this.testResultStatusConverter = testResultStatusConverter;
    }

    @Override
    public SubmissionResult convert(SybonSubmitResult submission) {
        //todo: FIX THIS PART, SYBON IS BUGGED HERE
        if (submission.getBuildResult() == null)
            return SubmissionResult.builder()
                    .built(false)
                    .verdict(Verdict.SERVER_ERROR)
                    .build();

        if (submission.getBuildResult().getStatus() == SybonBuildResult.Status.FAILED)
            return SubmissionResult.builder()
                .built(false)
                .buildInfo(submission.getBuildResult().getOutput())
                .verdict(Verdict.COMPILE_ERROR)
                .build();

        int maxTimeUsedMillis = 0;
        int maxMemoryUsedBytes = 0;
        Verdict verdict = Verdict.OK;
        Integer testsPassed = 0;
        for (SybonTestGroupResult testGroup : submission.getTestResults()) {
            for (SybonTestResult result : testGroup.getTestResults()) {
                SybonResourceUsage res = result.getResourceUsage();
                maxTimeUsedMillis = Math.max(maxTimeUsedMillis, res.getTimeUsageMillis());
                maxMemoryUsedBytes = Math.max(maxMemoryUsedBytes, res.getMemoryUsageBytes());
                if (result.getStatus() != SybonTestResultStatus.OK) {
                    verdict = testResultStatusConverter.convert(result.getStatus());
                    break;
                }
                testsPassed++;
            }
            if (verdict != Verdict.OK) break;
        }
        if (verdict == Verdict.OK) testsPassed = null;

        List<TestGroupResult> testGroups = submission.getTestResults().stream().map(this::convertTestGroupResult).collect(toList());
        return SubmissionResult.builder()
                .built(true)
                .buildInfo(submission.getBuildResult().getOutput())
                .verdict(verdict)
                .testsPassed(testsPassed)
                .timeUsedMillis(maxTimeUsedMillis)
                .memoryUsedBytes(maxMemoryUsedBytes)
                .testGroupResults(testGroups)
                .build();
    }

    private TestGroupResult convertTestGroupResult(SybonTestGroupResult result) {
        List<TestResult> results = result.getTestResults().stream()
                .map(this::convertTestResult)
                .collect(toList());
        return new TestGroupResult(result.getExecuted(), results);
    }

    private TestResult convertTestResult(SybonTestResult res) {
        return TestResult.builder()
                .verdict(testResultStatusConverter.convert(res.getStatus()))
                .judgeMessage(res.getJudgeMessage())
                .input(res.getInput())
                .output(res.getActualResult())
                .expected(res.getExpectedResult())
                .timeUsedMillis(res.getMillis())
                .memoryUsedBytes(res.getBytes())
                .build();
    }
}