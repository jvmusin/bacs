package istu.bacs.standings;

import istu.bacs.contest.Contest;

public interface StandingsService {
    Standings getStandings(Contest contest);
}