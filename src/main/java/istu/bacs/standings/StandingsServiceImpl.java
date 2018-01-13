package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;
import istu.bacs.submission.Verdict;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.submission.Verdict.*;
import static java.util.Arrays.asList;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    private static final Set<Verdict> unacceptableVerdicts = new HashSet<>(asList(NOT_SUBMITTED, PENDING, COMPILE_ERROR));

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