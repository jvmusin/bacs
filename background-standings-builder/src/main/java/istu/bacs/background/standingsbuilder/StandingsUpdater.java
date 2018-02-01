package istu.bacs.background.standingsbuilder;

import istu.bacs.background.standingsbuilder.config.StandingsRedisTemplate;
import istu.bacs.background.standingsbuilder.db.SubmissionService;
import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.rabbit.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static istu.bacs.background.standingsbuilder.StandingsServiceImpl.KEY;
import static istu.bacs.rabbit.QueueName.CHECKED_SUBMISSIONS;

@Slf4j
public class StandingsUpdater implements ApplicationListener<ContextRefreshedEvent> {

    private static final int tickDelay = 500;
    //print state once per 5 minutes
    private static final int printStateEveryNTicks = (1000 / tickDelay) * 60 * 5;

    private final StandingsRedisTemplate standingsRedisTemplate;
    private final SubmissionService submissionService;
    private final RabbitService rabbitService;

    private final Map<Integer, Standings> standingsByContestId = new ConcurrentHashMap<>();
    private final Queue<Integer> updatedStandings = new ConcurrentLinkedDeque<>();

    private final AtomicBoolean initialized = new AtomicBoolean();
    private final AtomicInteger tickCount = new AtomicInteger();

    public StandingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        this.standingsRedisTemplate = standingsRedisTemplate;
        this.submissionService = submissionService;
        this.rabbitService = rabbitService;
    }

    private Standings getOrCreateStandings(Contest contest) {
        return standingsByContestId.computeIfAbsent(contest.getContestId(), cId -> new Standings(contest));
    }

    @Scheduled(fixedDelay = tickDelay)
    void updateStandings() {
        if (updatedStandings.isEmpty()) {
            if (tickCount.incrementAndGet() == printStateEveryNTicks) {
                log.info("No need to update standings");
                tickCount.set(0);
            }
            return;
        }

        tickCount.set(0);
        log.info("Standings updating started");

        Set<Integer> updatedContests = new HashSet<>();
        while (!updatedStandings.isEmpty()) updatedContests.add(updatedStandings.poll());

        for (int contestId : updatedContests) {
            log.debug("Updating standings for contest {} started", contestId);

            Standings standings = standingsByContestId.get(contestId);
            standingsRedisTemplate.opsForHash().put(KEY, contestId, standings.toDto());

            log.debug("Updating standings for contest {} finished", contestId);
        }

        log.info("Standings updating finished");
    }

    private void update(Submission submission) {
        Standings standings = getOrCreateStandings(submission.getContest());
        standings.update(submission);
        updatedStandings.add(submission.getContest().getContestId());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized.getAndSet(true))
            return;

        submissionService.findAll().forEach(this::update);
        rabbitService.<Integer>subscribe(CHECKED_SUBMISSIONS,
                submissionId -> update(submissionService.findById(submissionId)));
    }
}