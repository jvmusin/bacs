package istu.bacs.contest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final ContestProblemRepository contestProblemRepository;

    public ContestServiceImpl(ContestRepository contestRepository, ContestProblemRepository contestProblemRepository) {
        this.contestRepository = contestRepository;
        this.contestProblemRepository = contestProblemRepository;
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

    @Override
    public ContestProblem findProblem(Contest contest, String problemIndex) {
        return contestProblemRepository.findByContestAndProblemIndex(contest, problemIndex);
    }
}