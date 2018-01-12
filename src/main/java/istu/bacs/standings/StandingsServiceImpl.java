package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import istu.bacs.submission.Verdict;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.submission.Verdict.*;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    private static Set<Verdict> unacceptableVerdicts = new HashSet<>(Arrays.asList(NOT_SUBMITTED, PENDING, COMPILE_ERROR));

    public StandingsServiceImpl(SubmissionService submissionService) {
        submissionService.subscribeOnSolutionTested(this::update);
    }

    @Override
    public void update(List<Submission> submissions) {
        submissions.forEach(this::update);
    }

    private void update(Submission submission) {
        if (!unacceptableVerdicts.contains(submission.getVerdict()))
            active.computeIfAbsent(submission.getContest(), Standings::new).update(submission);
    }

    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, Standings::new);
    }
}