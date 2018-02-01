package istu.bacs.web.model;

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