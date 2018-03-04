package istu.bacs.web.model.contest.standings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSolvingResult {
    private String problemIndex;
    private boolean solved;
    private int failTries;
    private int solvedAt;
}
