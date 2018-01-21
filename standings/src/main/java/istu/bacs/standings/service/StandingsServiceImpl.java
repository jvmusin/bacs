package istu.bacs.standings.service;

import istu.bacs.db.submission.Submission;
import istu.bacs.standings.Standings;
import istu.bacs.standings.db.SubmissionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.rabbit.QueueNames.CHECKED_SUBMISSIONS;

public class StandingsServiceImpl implements StandingsService, ApplicationListener<ContextRefreshedEvent> {

    private final SubmissionService submissionService;
    private final Map<Integer, Standings> standingsByContestId = new ConcurrentHashMap<>();

    public StandingsServiceImpl(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Override
    public void update(Submission submission) {
        Standings standings = getStandings(submission.getContest().getContestId());
        standings.update(submission);
    }

    @RabbitListener(queues = CHECKED_SUBMISSIONS)
    @Transactional
    public void update(int submissionId) {
        update(submissionService.findById(submissionId));
    }

    @Override
    public Standings getStandings(int contestId) {
        return standingsByContestId.computeIfAbsent(contestId, cId -> new Standings());
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAll().forEach(this::update);
    }
}