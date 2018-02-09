package istu.bacs.standings;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.rabbit.QueueName;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.standings.config.StandingsRedisTemplate;
import istu.bacs.standings.db.SubmissionService;
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

import static istu.bacs.standings.StandingsServiceImpl.KEY;

@Slf4j
public class StandingsUploader implements ApplicationListener<ContextRefreshedEvent> {

    private static final int TICK_DELAY = 500;
    //print state once per 5 minutes
    private static final int PRINT_STATE_EVERY_N_TICKS = (1000 / TICK_DELAY) * 60 * 5;

    private final StandingsRedisTemplate standingsRedisTemplate;
    private final SubmissionService submissionService;
    private final RabbitService rabbitService;

    private final Map<Contest, Standings> standingsByContest = new ConcurrentHashMap<>();
    private final Queue<Contest> updatedContests = new ConcurrentLinkedDeque<>();

    private final AtomicBoolean initialized = new AtomicBoolean();
    private final AtomicInteger tickCount = new AtomicInteger();

    public StandingsUploader(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        this.standingsRedisTemplate = standingsRedisTemplate;
        this.submissionService = submissionService;
        this.rabbitService = rabbitService;
    }

    private Standings getOrCreateStandings(Contest contest) {
        return standingsByContest.computeIfAbsent(contest, Standings::new);
    }

    @Scheduled(fixedDelay = TICK_DELAY)
    void uploadStandings() {

        if (!initialized.get())
            return;

        if (updatedContests.isEmpty()) {
            if (tickCount.incrementAndGet() == PRINT_STATE_EVERY_N_TICKS) {
                log.info("No need to upload standings");
                tickCount.set(0);
            }
            return;
        }

        tickCount.set(0);
        log.info("Standings uploading started");

        Set<Contest> needUpload = new HashSet<>();
        while (!updatedContests.isEmpty()) needUpload.add(updatedContests.poll());

        for (Contest contest : needUpload) {
            log.debug("Uploading standings for contest {} started", contest.getContestId());

            Standings standings = standingsByContest.get(contest);
            standingsRedisTemplate.opsForHash().put(KEY, contest.getContestId(), standings.toDto());

            log.debug("Uploading standings for contest {} finished", contest.getContestId());
        }

        log.info("Standings uploading finished");
    }

    private void update(Submission submission) {
        Standings standings = getOrCreateStandings(submission.getContest());
        standings.update(submission);
        updatedContests.add(submission.getContest());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized.get())
            return;

        submissionService.findAll().forEach(this::update);
        rabbitService.<Integer>subscribe(QueueName.CHECKED_SUBMISSIONS,
                submissionId -> update(submissionService.findById(submissionId)));

        initialized.set(true);
    }
}