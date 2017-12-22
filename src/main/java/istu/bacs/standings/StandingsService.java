package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.standings.Standings;

public interface StandingsService {
    Standings getStandings(Contest contest);
}