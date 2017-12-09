package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.model.Standings;
import istu.bacs.model.Submission;
import istu.bacs.service.SubmissionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StandingsServiceImpl implements istu.bacs.service.StandingsService {

    private final SubmissionService submissionService;

    public StandingsServiceImpl(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    private final ConcurrentHashMap<Contest, Standings> active = new ConcurrentHashMap<>();
    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, key -> Standings.empty(contest));
    }

    @Scheduled(fixedDelay = 10_000)
    private void updateStandings() {
        System.err.println("STANDINGS ARE UPDATING");
        active.keySet().parallelStream().forEach(contest -> {
            List<Submission> submissions = submissionService.findAllByContest(contest);
            Standings standings = new Standings(contest, submissions);
            active.put(contest, standings);
        });
        System.err.println("STANDINGS ARE UPDATED");
    }
}