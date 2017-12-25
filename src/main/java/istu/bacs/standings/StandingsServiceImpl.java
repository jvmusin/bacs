package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.submission.Verdict.PENDING;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    public StandingsServiceImpl(SubmissionService submissionService) {
        submissionService.subscribeOnSolutionTested(this::update);
    }

    @Override
    public void update(List<Submission> submissions) {
        submissions.forEach(this::update);
    }

    private void update(Submission submission) {
        Assert.isTrue(submission.getVerdict() != PENDING, "Submission should be tested at this moment");
        active.computeIfAbsent(submission.getContest(), Standings::new).update(submission);
    }

    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, Standings::new);
    }
}