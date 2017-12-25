package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;
import istu.bacs.user.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static istu.bacs.submission.Verdict.COMPILE_ERROR;
import static istu.bacs.submission.Verdict.OK;
import static java.util.Comparator.naturalOrder;

public class Standings {

    private final Contest contest;

    private final Map<User, ContestantRow> byUser = new HashMap<>();
    private List<ContestantRow> rows = new ArrayList<>();

    public Standings(Contest contest) {
        this.contest = contest;
    }

    public List<ContestantRow> getRows() {
        return rows;
    }

    public void update(Submission submission) {
        synchronized (this) {
            byUser.computeIfAbsent(submission.getAuthor(), ContestantRow::new).update(submission);

            List<ContestantRow> rows = new ArrayList<>(byUser.values());
            rows.sort(naturalOrder());
            for (int i = 0; i < rows.size(); i++) {
                ContestantRow row = rows.get(i);
                row.place = i + 1;

                if (i > 0) {
                    ContestantRow prev = rows.get(i - 1);
                    if (prev.compareTo(row) == 0) row.place = prev.place;
                }
            }

            this.rows = rows;
        }
    }

    @Data
    public static class SolvingResult {
        private static final int TRY_PENALTY_MINUTES = 20;

        private boolean solved;
        private int failTries;
        private int penalty;

        public SolvingResult(boolean solved, int failTries, int penalty) {
            this.solved = solved;
            this.failTries = failTries;
            this.penalty = penalty;
        }

        public static SolvingResult notSolved(int failTries) {
            return new SolvingResult(false, failTries, 0);
        }

        public static SolvingResult solved(int failTries, Submission last) {
            LocalDateTime startTime = last.getContest().getStartTime();
            int penalty = (int) Duration.between(startTime, last.getCreationTime()).toMinutes();
            return new SolvingResult(true, failTries, penalty + failTries * TRY_PENALTY_MINUTES);
        }
    }

    @Data
    public static class ProblemProgress {
        private final List<Submission> submissions = new ArrayList<>();

        private SolvingResult result = SolvingResult.notSolved(0);

        public SolvingResult update(Submission newSubmission) {
            submissions.add(newSubmission);

            shiftUpLast();

            int failTries = 0;
            for (Submission submission : submissions) {
                if (submission.getVerdict() == COMPILE_ERROR) continue;
                if (submission.getVerdict() == OK) return SolvingResult.solved(failTries, submission);
                else failTries++;
            }

            return result = SolvingResult.notSolved(failTries);
        }

        private void shiftUpLast() {
            int at = submissions.size() - 1;
            while (at > 0) {
                Submission prev = submissions.get(at - 1);
                Submission cur = submissions.get(at);
                if (cur.equals(prev)) {
                    submissions.remove(--at);
                    continue;
                }
                if (cur.getCreationTime().isBefore(prev.getCreationTime())) {
                    Collections.swap(submissions, at - 1, at);
                    at--;
                } else {
                    break;
                }
            }
        }
    }

    @Data
    public class ContestantRow implements Comparable<ContestantRow> {
        private User contestant;
        private ProblemProgress[] progresses;
        private int solvedCount;
        private int penalty;

        private int place;

        public ContestantRow(User contestant) {
            this.contestant = contestant;
            int problemCount = contest.getProblems().size();
            progresses = new ProblemProgress[problemCount];
            for (int i = 0; i < problemCount; i++)
                progresses[i] = new ProblemProgress();
        }

        public void update(Submission submission) {
            int problemIndex = contest.getProblems().indexOf(submission.getProblem());
            ProblemProgress progress = progresses[problemIndex];

            SolvingResult wasResult = progress.result;
            if (wasResult.solved) {
                solvedCount--;
                penalty -= wasResult.penalty;
            }

            SolvingResult result = progress.update(submission);
            if (result.solved) {
                solvedCount++;
                penalty += result.penalty;
            }
        }

        @Override
        public int compareTo(@NotNull ContestantRow other) {
            int c = -Integer.compare(solvedCount, other.solvedCount);
            if (c == 0) c = Integer.compare(penalty, other.penalty);
            return c;
        }
    }
}