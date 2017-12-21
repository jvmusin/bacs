package istu.bacs.service;

import istu.bacs.domain.Problem;

import java.util.List;

public interface ProblemService {
    Problem findById(String problemId);
	List<Problem> findAll();
    void save(Problem problem);
	void saveAll(List<Problem> problems);
}