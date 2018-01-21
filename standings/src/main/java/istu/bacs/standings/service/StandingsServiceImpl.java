package istu.bacs.standings.service;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;
import istu.bacs.standings.Standings;
import istu.bacs.standings.db.SubmissionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.transaction.Transactional;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.db.submission.Verdict.*;
import static istu.bacs.rabbit.QueueNames.CHECKED_SUBMISSIONS;

public class StandingsServiceImpl implements StandingsService, ApplicationListener<ContextRefreshedEvent> {

    private static final Set<Verdict> unacceptableVerdicts = EnumSet.of(NOT_SUBMITTED, PENDING, COMPILE_ERROR);

    private final SubmissionService submissionService;
    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    public StandingsServiceImpl(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Override
    public void update(Submission submission) {
        if (!unacceptableVerdicts.contains(submission.getVerdict()))
            getStandings(submission.getContest()).update(submission);
    }

    @RabbitListener(queues = CHECKED_SUBMISSIONS)
    @Transactional
    public void update(int submissionId) {
        update(submissionService.findById(submissionId));
    }

    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, c -> new Standings());
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAll().forEach(this::update);
    }
}