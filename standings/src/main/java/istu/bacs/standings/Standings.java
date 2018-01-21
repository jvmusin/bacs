package istu.bacs.standings;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

@Log
public class Standings {

    private final Map<User, ContestantRow> byUser = new HashMap<>();
    private final List<ContestantRow> rows = new ArrayList<>();

    public List<ContestantRow> getRows() {
        return rows;
    }

    public void update(Submission submission) {
        synchronized (this) {
            log.info("Updating standings for submission " + submission.getSubmissionId());
            byUser.computeIfAbsent(submission.getAuthor(), author -> {
                ContestantRow row = new ContestantRow(author, submission.getContest().getProblems());
                rows.add(row);
                return row;
            }).update(submission);

            normalizeRows();
        }
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
}