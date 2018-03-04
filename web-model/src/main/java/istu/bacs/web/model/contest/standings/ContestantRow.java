package istu.bacs.web.model.contest.standings;

import istu.bacs.web.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestantRow {
    private User author;
    private int place;
    private int solvedCount;
    private int penalty;
    private List<ProblemSolvingResult> results;
}