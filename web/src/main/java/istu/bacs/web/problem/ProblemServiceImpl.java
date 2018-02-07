package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    @Override
    public List<Problem> findAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public Problem findById(String problemId) {
        return problemRepository.findById(problemId).orElse(null);
    }

    @Override
    public void saveAll(Iterable<Problem> problems) {
        problemRepository.saveAll(problems);
    }
}