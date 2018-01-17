package istu.bacs.web.problem;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll();
    Problem findById(String problemId);
    ContestProblem findByContestAndProblemIndex(int contestId, String problemIndex);

    void saveAll(List<Problem> problems);
}