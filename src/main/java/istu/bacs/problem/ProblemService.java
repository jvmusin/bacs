package istu.bacs.problem;

import istu.bacs.contest.ContestProblem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll();
    Problem findById(String problemId);
    ContestProblem findByContestAndProblemIndex(int contestId, String problemIndex);

    Problem save(Problem problem);
    List<Problem> saveAll(List<Problem> problems);
}