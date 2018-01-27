package istu.bacs.web.model;

import lombok.Value;

@Value
public class ContestantRow {
    User author;
    int place;
    int solvedCount;
    int penalty;
    ProblemSolvingResult[] results;
}