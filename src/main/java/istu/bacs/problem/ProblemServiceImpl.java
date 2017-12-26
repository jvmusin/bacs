package istu.bacs.problem;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public Problem findById(String problemId) {
        return problemRepository.findById(problemId).orElse(null);
    }

    @Override
    public void save(Problem problem) {
        problemRepository.save(problem);
    }

    @Override
    public void saveAll(List<Problem> problems) {
        problemRepository.saveAll(problems);
    }
}