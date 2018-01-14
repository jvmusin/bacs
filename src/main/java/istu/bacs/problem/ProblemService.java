package istu.bacs.problem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll();
    Problem findById(String problemId);
    Problem save(Problem problem);
    List<Problem> saveAll(List<Problem> problems);
}