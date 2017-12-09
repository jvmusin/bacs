package istu.bacs.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

import static istu.bacs.model.Verdict.OK;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Data
public class Standings {

    private final Contest contest;
    private final List<ContestantRow> rows;

    public Standings(Contest contest, List<Submission> submissions) {
        this.contest = contest;
        Map<User, ContestantRow> byUser = new HashMap<>();
        submissions.stream().sorted(Comparator.comparing(Submission::getCreationTime))
                .forEach(submission -> byUser.computeIfAbsent(submission.getAuthor(), ContestantRow::new).update(submission));
        rows = byUser.values().stream().sorted().collect(toList());
    }

    public static Standings empty(Contest contest) {
        return new Standings(contest, emptyList());
    }

    @Data
    public class ContestantRow implements Comparable<ContestantRow> {
        private User contestant;
        private SolvingResult[] results;
        private int solvedCount;
        private int penalty;

        public ContestantRow(User contestant) {
            this.contestant = contestant;
            int problemCount = contest.getProblems().size();
            results = new SolvingResult[problemCount];
            for (int i = 0; i < problemCount; i++)
                results[i] = new SolvingResult();
        }

        public void update(Submission submission) {
            int problemIndex = contest.getProblems().indexOf(submission.getProblem());
            if (results[problemIndex].update(submission.getVerdict(), submission.getCreationTime())) {
                solvedCount++;
                penalty += results[problemIndex].penalty;
            }
        }

        @Override
        public int compareTo(ContestantRow other) {
            int c = Integer.compare(solvedCount, other.solvedCount);
            if (c == 0) c = Integer.compare(penalty, other.penalty);
            return c;
        }
    }

    @Data
    public class SolvingResult {
        private static final int TRY_PENALTY_MINUTES = 20;
        private boolean solved;
        private int failTries;
        private int penalty;

        public boolean update(Verdict verdict, LocalDateTime at) {
            if (solved) return false;
            if (verdict == OK) {
                penalty = (int) contest.getTimeSinceContestStart(at).toMinutes() + failTries * TRY_PENALTY_MINUTES;
                return solved = true;
            }
            failTries++;
            return false;
        }
    }
}