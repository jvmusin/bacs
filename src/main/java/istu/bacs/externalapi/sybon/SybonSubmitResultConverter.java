package istu.bacs.externalapi.sybon;

import istu.bacs.model.SubmissionResult;
import istu.bacs.model.TestGroupResult;
import istu.bacs.model.TestResult;
import istu.bacs.model.Verdict;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
class SybonSubmitResultConverter implements Converter<SybonSubmitResult, SubmissionResult> {
    @Override
    public SubmissionResult convert(SybonSubmitResult submission) {
        if (submission.getBuildResult() == null)
            return new SubmissionResult(false, "", Verdict.SERVER_ERROR, null, 0, 0, emptyList());
        //todo: FIX THIS PART, SYBON IS BUGGED HERE
        if (submission.getBuildResult().getStatus() == SybonBuildResult.Status.FAILED) {
            String status = submission.getBuildResult().getOutput();
            return new SubmissionResult(false, status, Verdict.COMPILE_ERROR, null, 0, 0, emptyList());
        }

        int timeUsedMillis = 0;
        int memoryUsedBytes = 0;
        Verdict verdict = Verdict.OK;
        Integer firstFailedTest = 0;
        for (SybonTestGroupResult testGroup : submission.getTestResults()) {
            for (SybonTestResult result : testGroup.getTestResults()) {
                firstFailedTest++;
                SybonResourceUsage res = result.getResourceUsage();
                timeUsedMillis = Math.max(timeUsedMillis, res.getTimeUsageMillis());
                memoryUsedBytes = Math.max(memoryUsedBytes, res.getMemoryUsageBytes());
                if (result.getStatus() != SybonTestResultStatus.OK) {
                    verdict = convertVerdict(result.getStatus());
                    break;
                }
            }
            if (verdict != Verdict.OK) break;
        }
        if (verdict == Verdict.OK) firstFailedTest = null;

        List<TestGroupResult> testGroups = submission.getTestResults().stream().map(this::convertTestGroupResult).collect(toList());
        return new SubmissionResult(
                true,
                submission.getBuildResult().getOutput(),
                verdict,
                firstFailedTest,
                timeUsedMillis,
                memoryUsedBytes,
                testGroups
        );
    }

    private Verdict convertVerdict(SybonTestResultStatus res) {
        switch (res) {
            case OK:                                    return Verdict.OK;
            case WRONG_ANSWER:                          return Verdict.WRONG_ANSWER;
            case PRESENTATION_ERROR:                    return Verdict.PRESENTATION_ERROR;
            case QUERIES_LIMIT_EXCEEDED:                return Verdict.QUERIES_LIMIT_EXCEEDED;
            case INCORRECT_REQUEST:                     return Verdict.INCORRECT_REQUEST;
            case INSUFFICIENT_DATA:                     return Verdict.INSUFFICIENT_DATA;
            case EXCESS_DATA:                           return Verdict.EXCESS_DATA;
            case OUTPUT_LIMIT_EXCEEDED:                 return Verdict.OUTPUT_LIMIT_EXCEEDED;
            case TERMINATION_REAL_TIME_LIMIT_EXCEEDED:  return Verdict.TERMINATION_REAL_TIME_LIMIT_EXCEEDED;
            case ABNORMAL_EXIT:                         return Verdict.ABNORMAL_EXIT;
            case MEMORY_LIMIT_EXCEEDED:                 return Verdict.MEMORY_LIMIT_EXCEEDED;
            case TIME_LIMIT_EXCEEDED:                   return Verdict.TIME_LIMIT_EXCEEDED;
            case REAL_TIME_LIMIT_EXCEEDED:              return Verdict.REAL_TIME_LIMIT_EXCEEDED;
            case TERMINATED_BY_SYSTEM:                  return Verdict.TERMINATED_BY_SYSTEM;
            case CUSTOM_FAILURE:                        return Verdict.CUSTOM_FAILURE;
            case FAIL_TEST:                             return Verdict.FAIL_TEST;
            case FAILED:                                return Verdict.FAILED;
            case SKIPPED:                               return Verdict.SKIPPED;
            default: throw new RuntimeException("No matching verdict: " + res);
        }
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
                convertVerdict(res.getStatus()),
                res.getJudgeMessage(),
                res.getInput(),
                res.getActualResult(),
                res.getExpectedResult(),
                res.getResourceUsage().getTimeUsageMillis(),
                res.getResourceUsage().getMemoryUsageBytes()
        );
    }
}