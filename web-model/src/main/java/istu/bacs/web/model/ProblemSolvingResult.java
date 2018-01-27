package istu.bacs.web.model;

import lombok.Value;

@Value
public class ProblemSolvingResult {
    String problemIndex;
    boolean solved;
    int failTries;
    int solvedAt;
}
