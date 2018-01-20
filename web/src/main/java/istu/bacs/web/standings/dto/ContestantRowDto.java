package istu.bacs.web.standings.dto;

import istu.bacs.standings.ContestantRow;
import istu.bacs.web.user.dto.UserDto;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class ContestantRowDto {

    private UserDto contestant;
    private List<SolvingResultDto> results;
    private int solvedCount;
    private int penalty;
    private int place;

    public ContestantRowDto(ContestantRow row) {
        contestant = new UserDto(row.getContestant());
        results = row.getProgressByProblem().entrySet().stream()
                .map(e -> new SolvingResultDto(e.getKey().getProblemIndex(), e.getValue().getResult()))
                .collect(toList());

        solvedCount = row.getSolvedCount();
        penalty = row.getPenalty();
        place = row.getPlace();
    }
}