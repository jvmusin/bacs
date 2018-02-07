package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAllProblems();
    Problem findById(String problemId);
    void saveAll(Iterable<Problem> problems);
}