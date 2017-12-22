package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final SubmissionService submissionService;

    public StandingsServiceImpl(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    private final ConcurrentHashMap<Contest, Standings> active = new ConcurrentHashMap<>();
    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, Standings::empty);
    }

    @Scheduled(fixedDelay = 10_000)
    private void updateStandings() {
        System.err.println("STANDINGS ARE UPDATING");
        active.keySet().parallelStream().forEach(contest -> {
            List<Submission> submissions = submissionService.findAllByContest(contest.getContestId());
            Standings standings = new Standings(contest, submissions);
            active.put(contest, standings);
        });
        System.err.println("STANDINGS ARE UPDATED");
    }
}