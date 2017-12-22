package istu.bacs.problem;

import java.util.List;

public interface ProblemService {
    Problem findById(String problemId);
	List<Problem> findAll();
    void save(Problem problem);
	void saveAll(List<Problem> problems);
}