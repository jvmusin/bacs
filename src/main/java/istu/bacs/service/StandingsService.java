package istu.bacs.service;

import istu.bacs.domain.Contest;
import istu.bacs.domain.Standings;

public interface StandingsService {
    Standings getStandings(Contest contest);
}