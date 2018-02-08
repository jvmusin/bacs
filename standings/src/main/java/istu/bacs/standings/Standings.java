package istu.bacs.standings;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.db.user.User;
import istu.bacs.web.model.contest.Contest;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static istu.bacs.db.submission.Verdict.*;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Slf4j
public class Standings {

    private static final Set<Verdict> unacceptableVerdicts = EnumSet.of(SCHEDULED, PENDING, COMPILE_ERROR);

    private final Contest contest;
    private final Map<User, ContestantRow> rowByAuthor = new HashMap<>();
    private final List<ContestantRow> rows = new ArrayList<>();

    public Standings(istu.bacs.db.contest.Contest contest) {
        this.contest = Contest.fromDb(contest);
    }

    @Synchronized
    public void update(Submission submission) {
        log.debug("Updating standings for submission {} started", submission.getSubmissionId());

        if (unacceptableVerdicts.contains(submission.getVerdict()))
            return;

        rowByAuthor.computeIfAbsent(submission.getAuthor(), author -> {
            ContestantRow row = new ContestantRow(author, submission.getContest().getProblems());
            rows.add(row);
            return row;
        }).update(submission);

        normalizeRows();

        log.debug("Updating standings for submission {} finished", submission.getSubmissionId());
    }

    private void normalizeRows() {
        rows.sort(comparingInt((ContestantRow contestantRow) -> -contestantRow.getSolvedCount())
                .thenComparingInt(ContestantRow::getPenalty));

        for (int i = 0; i < rows.size(); i++) {
            ContestantRow row = rows.get(i);
            row.setPlace(i + 1);

            if (i > 0) {
                ContestantRow prev = rows.get(i - 1);
                if (prev.getSolvedCount() == row.getSolvedCount() && prev.getPenalty() == row.getPenalty())
                    row.setPlace(prev.getPlace());
            }
        }
    }

    @Synchronized
    public istu.bacs.web.model.contest.standings.Standings toDto() {
        return new istu.bacs.web.model.contest.standings.Standings(
                contest,
                rows.stream()
                        .map(ContestantRow::toDto)
                        .collect(toList())
        );
    }
}