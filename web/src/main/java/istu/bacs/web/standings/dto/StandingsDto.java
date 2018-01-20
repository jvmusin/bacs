package istu.bacs.web.standings.dto;

import istu.bacs.standings.Standings;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class StandingsDto {

    private List<ContestantRowDto> contestants;

    public StandingsDto(Standings standings) {
        contestants = standings.getRows().stream()
                .map(ContestantRowDto::new)
                .collect(toList());
    }
}