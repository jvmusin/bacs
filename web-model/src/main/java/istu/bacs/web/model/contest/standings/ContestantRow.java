package istu.bacs.web.model.contest.standings;

import istu.bacs.web.model.user.User;
import lombok.Value;

import java.util.List;

@Value
public class ContestantRow {
    User author;
    int place;
    int solvedCount;
    int penalty;
    List<ProblemSolvingResult> results;
}