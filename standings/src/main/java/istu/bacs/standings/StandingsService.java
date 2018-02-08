package istu.bacs.standings;

import istu.bacs.web.model.get.Standings;

public interface StandingsService {
    Standings getStandings(int contestId);
}