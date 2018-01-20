package istu.bacs.web.standings.dto;

import istu.bacs.standings.SolvingResult;
import lombok.Data;

@Data
public class SolvingResultDto {

    private String problemIndex;

    private boolean solved;
    private int failTries;
    private int penalty;

    public SolvingResultDto(String problemIndex, SolvingResult result) {
        this.problemIndex = problemIndex;

        solved = result.isSolved();
        failTries = result.getFailTries();
        penalty = result.getPenalty();
    }
}