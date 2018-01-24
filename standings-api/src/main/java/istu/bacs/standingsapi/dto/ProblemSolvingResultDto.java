package istu.bacs.standingsapi.dto;

import lombok.Data;

@Data
public class ProblemSolvingResultDto {

    private String problemIndex;

    private boolean solved;
    private int failTries;
    private int solvedAt;
}