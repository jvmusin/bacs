package istu.bacs.service;

import istu.bacs.model.Problem;

import java.util.List;

public interface ProblemService {
    Problem findById(String problemId);
	List<Problem> findAll();
    void updateProblems(List<Problem> problems);
    void save(Problem problem);
	void saveAll(List<Problem> problems);
}