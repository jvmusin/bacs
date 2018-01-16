package istu.bacs.web.standings;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.db.submission.Verdict.*;

@Service
public class StandingsServiceImpl implements StandingsService {

    private static final Set<Verdict> unacceptableVerdicts = EnumSet.of(NOT_SUBMITTED, PENDING, COMPILE_ERROR);
    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    @Override
    public void update(List<Submission> submissions) {
        submissions.forEach(this::update);
    }

    @Override
    public void update(Submission submission) {
        if (!unacceptableVerdicts.contains(submission.getVerdict()))
            getStandings(submission.getContest()).update(submission);
    }

    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, Standings::new);
    }
}