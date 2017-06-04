package istu.bacs.service;

import istu.bacs.model.Problem;

public interface ProblemService {
	
	Problem findById(Integer id);
	void save(Problem problem);
	
}