package istu.bacs.sybon;

import istu.bacs.db.submission.Verdict;
import org.springframework.core.convert.converter.Converter;

import java.util.EnumMap;
import java.util.Map;

public class SybonTestResultStatusConverter implements Converter<SybonTestResultStatus, Verdict> {

    private static final Map<SybonTestResultStatus, Verdict> mappings;

    static {
        mappings = new EnumMap<>(SybonTestResultStatus.class);
        mappings.put(SybonTestResultStatus.OK,                                   Verdict.OK);
        mappings.put(SybonTestResultStatus.WRONG_ANSWER,                         Verdict.WRONG_ANSWER);
        mappings.put(SybonTestResultStatus.PRESENTATION_ERROR,                   Verdict.PRESENTATION_ERROR);
        mappings.put(SybonTestResultStatus.QUERIES_LIMIT_EXCEEDED,               Verdict.QUERIES_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.INCORRECT_REQUEST,                    Verdict.INCORRECT_REQUEST);
        mappings.put(SybonTestResultStatus.INSUFFICIENT_DATA,                    Verdict.INSUFFICIENT_DATA);
        mappings.put(SybonTestResultStatus.EXCESS_DATA,                          Verdict.EXCESS_DATA);
        mappings.put(SybonTestResultStatus.OUTPUT_LIMIT_EXCEEDED,                Verdict.OUTPUT_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.TERMINATION_REAL_TIME_LIMIT_EXCEEDED, Verdict.TERMINATION_REAL_TIME_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.ABNORMAL_EXIT,                        Verdict.ABNORMAL_EXIT);
        mappings.put(SybonTestResultStatus.MEMORY_LIMIT_EXCEEDED,                Verdict.MEMORY_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.TIME_LIMIT_EXCEEDED,                  Verdict.TIME_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.REAL_TIME_LIMIT_EXCEEDED,             Verdict.REAL_TIME_LIMIT_EXCEEDED);
        mappings.put(SybonTestResultStatus.TERMINATED_BY_SYSTEM,                 Verdict.TERMINATED_BY_SYSTEM);
        mappings.put(SybonTestResultStatus.CUSTOM_FAILURE,                       Verdict.CUSTOM_FAILURE);
        mappings.put(SybonTestResultStatus.FAIL_TEST,                            Verdict.FAIL_TEST);
        mappings.put(SybonTestResultStatus.FAILED,                               Verdict.FAILED);
        mappings.put(SybonTestResultStatus.SKIPPED,                              Verdict.SKIPPED);
    }

    @Override
    public Verdict convert(SybonTestResultStatus status) {
        return mappings.get(status);
    }
}