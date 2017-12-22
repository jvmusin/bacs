package istu.bacs.externalapi.sybon;

import istu.bacs.submission.Verdict;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SybonTestResultStatusConverter implements Converter<SybonTestResultStatus, Verdict> {
    @Override
    public Verdict convert(@NotNull SybonTestResultStatus status) {
        switch (status) {
            case OK:                                   return Verdict.OK;
            case WRONG_ANSWER:                         return Verdict.WRONG_ANSWER;
            case PRESENTATION_ERROR:                   return Verdict.PRESENTATION_ERROR;
            case QUERIES_LIMIT_EXCEEDED:               return Verdict.QUERIES_LIMIT_EXCEEDED;
            case INCORRECT_REQUEST:                    return Verdict.INCORRECT_REQUEST;
            case INSUFFICIENT_DATA:                    return Verdict.INSUFFICIENT_DATA;
            case EXCESS_DATA:                          return Verdict.EXCESS_DATA;
            case OUTPUT_LIMIT_EXCEEDED:                return Verdict.OUTPUT_LIMIT_EXCEEDED;
            case TERMINATION_REAL_TIME_LIMIT_EXCEEDED: return Verdict.TERMINATION_REAL_TIME_LIMIT_EXCEEDED;
            case ABNORMAL_EXIT:                        return Verdict.ABNORMAL_EXIT;
            case MEMORY_LIMIT_EXCEEDED:                return Verdict.MEMORY_LIMIT_EXCEEDED;
            case TIME_LIMIT_EXCEEDED:                  return Verdict.TIME_LIMIT_EXCEEDED;
            case REAL_TIME_LIMIT_EXCEEDED:             return Verdict.REAL_TIME_LIMIT_EXCEEDED;
            case TERMINATED_BY_SYSTEM:                 return Verdict.TERMINATED_BY_SYSTEM;
            case CUSTOM_FAILURE:                       return Verdict.CUSTOM_FAILURE;
            case FAIL_TEST:                            return Verdict.FAIL_TEST;
            case FAILED:                               return Verdict.FAILED;
            case SKIPPED:                              return Verdict.SKIPPED;
            default: throw new RuntimeException("No matching verdict: " + status);
        }
    }
}