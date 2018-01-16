package istu.bacs.web.problem;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll();
    Problem findById(String problemId);
    ContestProblem findByContestAndProblemIndex(int contestId, String problemIndex);

    Problem save(Problem problem);
    List<Problem> saveAll(List<Problem> problems);
}