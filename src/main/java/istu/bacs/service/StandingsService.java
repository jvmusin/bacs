package istu.bacs.service;

import istu.bacs.model.Contest;
import istu.bacs.model.Standings;

public interface StandingsService {
    Standings getStandings(Contest contest);
}