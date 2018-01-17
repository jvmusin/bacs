package istu.bacs.web.standings;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;

public interface StandingsService {
    void update(Submission submission);
    Standings getStandings(Contest contest);
}