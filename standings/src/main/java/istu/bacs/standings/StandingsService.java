package istu.bacs.standings;

import istu.bacs.web.model.contest.standings.Standings;

public interface StandingsService {
    Standings getStandings(int contestId);
}