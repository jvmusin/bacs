package istu.bacs.standings;

import istu.bacs.standings.Standings;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StandingsDto {

    private String contestName;
    private List<String> problemLetters;
    private List<Row> rows;

    public StandingsDto(Standings standings) {
        contestName = standings.getContest().getContestName();

        problemLetters = new ArrayList<>();
        int problemCount = standings.getContest().getProblems().size();
        for (int i = 0; i < problemCount; i++) problemLetters.add("" + (char) ('A' + i));

        rows = new ArrayList<>();
        standings.getRows().forEach(row -> rows.add(new Row(row)));
    }

    @Data
    public class Row {
        private int place;
        private String contestant;
        private List<String> results;
        private int solvedCount;
        private int penalty;

        public Row(Standings.ContestantRow row) {
            place = rows.size() + 1;
            contestant = row.getContestant().getUsername();
            results = new ArrayList<>();
            for (int i = 0; i < row.getResults().length; i++) {
                Standings.SolvingResult cur = row.getResults()[i];
                int failTries = cur.getFailTries();
                if (cur.isSolved()) results.add("+" + (failTries == 0 ? "" : failTries));
                else if (failTries > 0) results.add("-" + failTries);
                else results.add("");
            }
            solvedCount = row.getSolvedCount();
            penalty = row.getPenalty();

            if (!rows.isEmpty() && rows.get(place - 1).solvedCount == solvedCount && rows.get(place - 1).penalty == penalty)
                place--;
        }
    }
}