package istu.bacs.standingsapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class StandingsDto {

    private List<ContestantRowDto> contestants;
}