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
    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    @Override
    public Problem findById(String problemId) {
        return problemRepository.findById(problemId).orElse(null);
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