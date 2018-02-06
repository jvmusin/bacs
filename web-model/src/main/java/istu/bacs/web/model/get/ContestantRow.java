package istu.bacs.web.model.get;

import istu.bacs.web.model.User;
import lombok.Value;

@Value
public class ContestantRow {
    User author;
    int place;
    int solvedCount;
    int penalty;
    ProblemSolvingResult[] results;
}