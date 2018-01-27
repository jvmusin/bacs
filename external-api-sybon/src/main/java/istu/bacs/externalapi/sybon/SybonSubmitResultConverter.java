package istu.bacs.externalapi.sybon;

import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.submission.Verdict;
import org.springframework.core.convert.converter.Converter;

import java.util.Base64;

public class SybonSubmitResultConverter implements Converter<SybonSubmitResult, SubmissionResult> {

    private static final SybonTestResultStatusConverter testResultStatusConverter = new SybonTestResultStatusConverter();

    @Override
    public SubmissionResult convert(SybonSubmitResult submission) {
        SybonBuildResultStatus status = submission.getBuildResult().getStatus();
        if (status == SybonBuildResultStatus.PENDING)
            return SubmissionResult.builder()
                    .verdict(Verdict.PENDING)
                    .build();

        String buildInfo = new String(Base64.getDecoder().decode(submission.getBuildResult().getOutput()));
        if (buildInfo.trim().isEmpty()) buildInfo = null;

        if (status == SybonBuildResultStatus.FAILED || status == SybonBuildResultStatus.SERVER_ERROR)
            return SubmissionResult.builder()
                    .buildInfo(buildInfo)
                    .verdict(Verdict.COMPILE_ERROR)
                    .build();

        int maxTimeUsedMillis = 0;
        int maxMemoryUsedBytes = 0;
        Verdict verdict = Verdict.ACCEPTED;
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
            if (verdict != Verdict.ACCEPTED) break;
        }
        if (verdict == Verdict.ACCEPTED) testsPassed = null;

        return SubmissionResult.builder()
                .buildInfo(buildInfo)
                .verdict(verdict)
                .testsPassed(testsPassed)
                .timeUsedMillis(maxTimeUsedMillis)
                .memoryUsedBytes(maxMemoryUsedBytes)
                .build();
    }
}