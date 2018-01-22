package istu.bacs.standingsapi;

import istu.bacs.standingsapi.dto.StandingsDto;

public interface StandingsService {

    StandingsDto getStandings(int contestId);
}