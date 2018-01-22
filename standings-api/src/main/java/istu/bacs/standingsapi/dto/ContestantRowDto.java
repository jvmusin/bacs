package istu.bacs.standingsapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContestantRowDto {

    private String username;
    private int place;

    private List<ProblemSolvingResultDto> results;
    private int solvedCount;
    private int penalty;
}