package istu.bacs.standingsbuilder;

import istu.bacs.db.submission.Submission;
import istu.bacs.rabbit.QueueNames;
import istu.bacs.standingsbuilder.config.StandingsRedisTemplate;
import istu.bacs.standingsbuilder.db.SubmissionService;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.standingsbuilder.StandingsServiceImpl.KEY;

@Log
public class StandingsUpdater implements ApplicationListener<ContextRefreshedEvent> {

    private final StandingsRedisTemplate standingsRedisTemplate;
    private final SubmissionService submissionService;

    private final Map<Integer, Standings> standingsByContestId = new ConcurrentHashMap<>();
    private final Queue<Integer> updatedStandings = new ConcurrentLinkedDeque<>();

    public StandingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService) {
        this.standingsRedisTemplate = standingsRedisTemplate;
        this.submissionService = submissionService;
    }

    private Standings getOrCreateStandings(int contestId) {
        return standingsByContestId.computeIfAbsent(contestId, cId -> new Standings());
    }

    @Scheduled(fixedDelay = 1000)
    void updateStandings() {
        if (updatedStandings.isEmpty())
            return;

        log.info("STANDINGS UPDATING STARTED");

        Set<Integer> changed = new HashSet<>();
        while (!updatedStandings.isEmpty()) changed.add(updatedStandings.poll());

        for (int contestId : changed) {
            log.info("UPDATING STANDINGS FOR CONTEST " + contestId + " STARTED");
            Standings standings = standingsByContestId.get(contestId);
            standingsRedisTemplate.opsForHash().put(KEY, contestId, standings.toDto());
            log.info("UPDATING STANDINGS FOR CONTEST " + contestId + " FINISHED");
        }

        log.info("STANDINGS UPDATING FINISHED");
    }

    private void update(Submission submission) {
        Standings standings = getOrCreateStandings(submission.getContest().getContestId());
        synchronized (this) {
            standings.update(submission);
        }
        updatedStandings.add(submission.getContest().getContestId());
    }

    @RabbitListener(queues = QueueNames.CHECKED_SUBMISSIONS)
    @Transactional
    void update(int submissionId) {
        update(submissionService.findById(submissionId));
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAll().forEach(this::update);
    }
}