package istu.bacs.service;

import istu.bacs.model.Problem;

public interface ProblemService {
	Problem findById(String problemId);
	void save(Problem problem);
	void saveAll(Iterable<Problem> problems);
}