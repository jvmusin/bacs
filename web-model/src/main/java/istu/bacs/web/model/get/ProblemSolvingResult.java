package istu.bacs.web.model.get;

import lombok.Value;

@Value
public class ProblemSolvingResult {
    String problemIndex;
    boolean solved;
    int failTries;
    int solvedAt;
}
