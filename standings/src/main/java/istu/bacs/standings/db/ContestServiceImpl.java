package istu.bacs.standings.db;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    @Override
    public Contest findById(int contestId) {
        return contestRepository.findById(contestId).orElse(null);
    }
}