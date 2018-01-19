package istu.bacs.web.standings.dto;

import istu.bacs.db.contest.Contest;
import istu.bacs.standings.Standings;
import istu.bacs.web.contest.dto.ContestMetaDto;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class StandingsDto {

    private ContestMetaDto contest;
    private List<ContestantRowDto> contestants;

    public StandingsDto(Contest contest, Standings standings) {
        this.contest = new ContestMetaDto(contest);
        contestants = standings.getRows().stream()
                .map(ContestantRowDto::new)
                .collect(toList());
    }
}