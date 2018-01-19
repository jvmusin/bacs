package istu.bacs.externalapi.sybonfake;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.submission.Verdict;

import java.util.Random;

import static istu.bacs.db.submission.Verdict.*;
import static istu.bacs.externalapi.sybonfake.Measure.MEGABYTE;
import static istu.bacs.externalapi.sybonfake.Measure.MILLISECOND;

public class SubmissionResultUpdater {

    private final Random rnd = new Random();

    public void updateSubmissionResult(Submission submission) {
        String solution = submission.getSolution().toLowerCase();
        int oldSubmissionResultId = submission.getResult().getSubmissionResultId();
        if (solution.contains("accepted")) {
            submission.setResult(accepted(submission));
        } else if (solution.contains("tl")) {
            submission.setResult(timeLimit(submission));
        } else if (solution.contains("ml")) {
            submission.setResult(memoryLimit(submission));
        } else if (solution.contains("ce")) {
            submission.setResult(compileError(submission));
        } else {
            Verdict verdict = tryFindVerdict(solution);
            if (verdict == null) submission.setResult(compileError(submission));
            else submission.setResult(withVerdict(submission, verdict));
        }
        submission.getResult().setSubmissionResultId(oldSubmissionResultId);
    }

    private SubmissionResult accepted(Submission submission) {
        return SubmissionResult.builder()
                .submissionResultId(rnd.nextInt(Integer.MAX_VALUE))
                .submission(submission)
                .verdict(ACCEPTED)
                .timeUsedMillis(rnd.nextInt(submission.getProblem().getTimeLimitMillis()) + 1)
                .memoryUsedBytes(rnd.nextInt(submission.getProblem().getMemoryLimitBytes()) + 1)
                .build();
    }

    private SubmissionResult timeLimit(Submission submission) {
        return SubmissionResult.builder()
                .submissionResultId(rnd.nextInt(Integer.MAX_VALUE))
                .submission(submission)
                .verdict(TIME_LIMIT_EXCEEDED)
                .testsPassed(rnd.nextInt(100) + 1)
                .timeUsedMillis(submission.getProblem().getTimeLimitMillis() + (rnd.nextInt(100) + 1) * MEGABYTE)
                .memoryUsedBytes(rnd.nextInt(submission.getProblem().getMemoryLimitBytes()) + 1)
                .build();
    }

    private SubmissionResult memoryLimit(Submission submission) {
        return SubmissionResult.builder()
                .submissionResultId(rnd.nextInt(Integer.MAX_VALUE))
                .submission(submission)
                .verdict(MEMORY_LIMIT_EXCEEDED)
                .testsPassed(rnd.nextInt(100) + 1)
                .timeUsedMillis(rnd.nextInt(submission.getProblem().getTimeLimitMillis()) + 1)
                .memoryUsedBytes(submission.getProblem().getMemoryLimitBytes() + (rnd.nextInt(100) + 1) * MILLISECOND)
                .build();
    }

    private SubmissionResult compileError(Submission submission) {
        return SubmissionResult.builder()
                .submissionResultId(rnd.nextInt(Integer.MAX_VALUE))
                .submission(submission)
                .buildInfo("Something is wrong")
                .verdict(COMPILE_ERROR)
                .build();
    }

    private SubmissionResult withVerdict(Submission submission, Verdict verdict) {
        return SubmissionResult.builder()
                .submissionResultId(rnd.nextInt(Integer.MAX_VALUE))
                .submission(submission)
                .verdict(ACCEPTED)
                .verdict(verdict)
                .timeUsedMillis(rnd.nextInt(submission.getProblem().getTimeLimitMillis()) + 1)
                .memoryUsedBytes(rnd.nextInt(submission.getProblem().getMemoryLimitBytes()) + 1)
                .build();
    }

    private Verdict tryFindVerdict(String solution) {
        solution = solution.toLowerCase();
        for (Verdict verdict : Verdict.values())
            if (solution.contains(verdict.name().toLowerCase()))
                return verdict;
        return null;
    }
}