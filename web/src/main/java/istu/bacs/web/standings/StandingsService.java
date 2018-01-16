package istu.bacs.web.standings;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;

import java.util.List;

public interface StandingsService {
    void update(List<Submission> submissions);
    void update(Submission submission);
    Standings getStandings(Contest contest);
}