package istu.bacs.standings;

import istu.bacs.contest.Contest;
import istu.bacs.contest.ContestService;
import istu.bacs.submission.Submission;
import istu.bacs.submission.Verdict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.submission.Verdict.*;
import static java.util.Arrays.asList;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final ContestService contestService;

    private final Map<Contest, Standings> active = new ConcurrentHashMap<>();

    private static final Set<Verdict> unacceptableVerdicts = new HashSet<>(asList(NOT_SUBMITTED, PENDING, COMPILE_ERROR));

    public StandingsServiceImpl(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    @Transactional
    public void update(List<Submission> submissions) {
        submissions.forEach(this::update);
    }

    @Override
    @Transactional
    public void update(Submission submission) {
        if (!unacceptableVerdicts.contains(submission.getVerdict())) {
            Contest contest = contestService.findById(submission.getContest().getContestId());  //todo: Костыль, но иначе не получается выгрузить задачи
            active.computeIfAbsent(contest, Standings::new).update(submission);
        }
    }

    @Override
    public Standings getStandings(Contest contest) {
        return active.computeIfAbsent(contest, Standings::new);
    }
}