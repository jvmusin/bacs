package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;

import java.util.List;

public interface StandingsService {
    void update(List<Submission> submissions);
    Standings getStandings(Contest contest);
}