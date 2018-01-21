package istu.bacs.standings.service;

import istu.bacs.db.submission.Submission;
import istu.bacs.standings.Standings;

public interface StandingsService {
    void update(Submission submission);
    Standings getStandings(int contestId);
}