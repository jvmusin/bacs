package istu.bacs.web.problem;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.contest.ContestProblemRepository;
import istu.bacs.db.problem.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final ContestProblemRepository contestProblemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository, ContestProblemRepository contestProblemRepository) {
        this.problemRepository = problemRepository;
        this.contestProblemRepository = contestProblemRepository;
    }

    @Override
    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    @Override
    public Problem findById(String problemId) {
        return problemRepository.findById(problemId).orElse(null);
    }

    @Override
    public ContestProblem findByContestAndProblemIndex(int contestId, String problemIndex) {
        Contest contest = Contest.builder().contestId(contestId).build();
        return contestProblemRepository.findByContestAndProblemIndex(contest, problemIndex);
    }

    @Override
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Override
    public List<Problem> saveAll(List<Problem> problems) {
        return problemRepository.saveAll(problems);
    }
}