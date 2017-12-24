package istu.bacs.externalapi.sybon;

import istu.bacs.submission.SubmissionResult;
import istu.bacs.submission.Verdict;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static istu.bacs.submission.Verdict.*;

@Component
class SybonSubmitResultConverter implements Converter<SybonSubmitResult, SubmissionResult> {

    private final SybonTestResultStatusConverter testResultStatusConverter;

    public SybonSubmitResultConverter(SybonTestResultStatusConverter testResultStatusConverter) {
        this.testResultStatusConverter = testResultStatusConverter;
    }

    @NotNull
    @Override
    public SubmissionResult convert(@NotNull SybonSubmitResult submission) {
        if (submission.getBuildResult() == null)
            return SubmissionResult.builder()
                    .submissionId(submission.getId())
                    .verdict(PENDING)
                    .build();

        if (submission.getBuildResult().getStatus() == SybonBuildResult.Status.Failed)
            return SubmissionResult.builder()
                    .submissionId(submission.getId())
                    .buildInfo(submission.getBuildResult().getOutput())
                    .verdict(COMPILE_ERROR)
                    .build();

        int maxTimeUsedMillis = 0;
        int maxMemoryUsedBytes = 0;
        Verdict verdict = OK;
        Integer testsPassed = 0;
        for (SybonTestGroupResult testGroup : submission.getTestGroupResults()) {
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
            if (verdict != OK) break;
        }
        if (verdict == OK) testsPassed = null;

        return SubmissionResult.builder()
                .submissionId(submission.getId())
                .built(true)
                .buildInfo(submission.getBuildResult().getOutput())
                .verdict(verdict)
                .testsPassed(testsPassed)
                .timeUsedMillis(maxTimeUsedMillis)
                .memoryUsedBytes(maxMemoryUsedBytes)
                .build();
    }
}