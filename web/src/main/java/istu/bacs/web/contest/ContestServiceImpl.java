package istu.bacs.web.contest;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public Contest findById(int contestId) {
        return contestRepository.findById(contestId).orElse(null);
    }

    @Override
    public List<Contest> findAll(Pageable pageable) {
        return contestRepository.findAll(pageable).getContent();
    }

    @Override
    public void save(Contest contest) {
        contestRepository.save(contest);
    }
}