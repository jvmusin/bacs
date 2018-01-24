package istu.bacs.background.standingsbuilder;

import istu.bacs.background.standingsbuilder.config.StandingsRedisTemplate;
import istu.bacs.background.standingsbuilder.db.SubmissionService;
import istu.bacs.db.submission.Submission;
import istu.bacs.rabbit.RabbitService;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static istu.bacs.background.standingsbuilder.StandingsServiceImpl.KEY;
import static istu.bacs.rabbit.QueueNames.CHECKED_SUBMISSIONS;
import static java.lang.Integer.parseInt;

@Log
public class StandingsUpdater implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private final StandingsRedisTemplate standingsRedisTemplate;
    private final SubmissionService submissionService;
    private final RabbitService rabbitService;

    private final Map<Integer, Standings> standingsByContestId = new ConcurrentHashMap<>();
    private final Queue<Integer> updatedStandings = new ConcurrentLinkedDeque<>();

    private StandingsUpdater self;

    public StandingsUpdater(StandingsRedisTemplate standingsRedisTemplate, SubmissionService submissionService, RabbitService rabbitService) {
        this.standingsRedisTemplate = standingsRedisTemplate;
        this.submissionService = submissionService;
        this.rabbitService = rabbitService;
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

    @Transactional
    public void update(int submissionId) {
        update(submissionService.findById(submissionId));
    }

    @PostConstruct
    public void registerSubmissionReceiver() {
        rabbitService.subscribe(CHECKED_SUBMISSIONS, m -> {
            int submissionId = parseInt(new String(m.getBody()));
            self.update(submissionId);
        });
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        submissionService.findAll().stream()
                .map(Submission::getSubmissionId)
                .forEach(self::update);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        self = applicationContext.getBean(StandingsUpdater.class);
    }
}