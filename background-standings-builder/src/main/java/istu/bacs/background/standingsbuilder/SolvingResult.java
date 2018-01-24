package istu.bacs.background.standingsbuilder;

import istu.bacs.db.submission.Submission;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class SolvingResult {

    static final int TRY_PENALTY_MINUTES = 20;

    private boolean solved;
    private int failTries;
    private int penalty;

    private SolvingResult(boolean solved, int failTries, int penalty) {
        this.solved = solved;
        this.failTries = failTries;
        this.penalty = penalty;
    }

    public static SolvingResult notSolved(int failTries) {
        return new SolvingResult(false, failTries, 0);
    }

    public static SolvingResult solved(int failTries, Submission last) {
        LocalDateTime startTime = last.getContest().getStartTime();
        int penalty = (int) Duration.between(startTime, last.getCreated()).toMinutes();
        return new SolvingResult(true, failTries, penalty + failTries * TRY_PENALTY_MINUTES);
    }
}