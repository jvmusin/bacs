package istu.bacs.web.model.contest.standings;

import lombok.Value;

@Value
public class ProblemSolvingResult {
    String problemIndex;
    boolean solved;
    int failTries;
    int solvedAt;
}
