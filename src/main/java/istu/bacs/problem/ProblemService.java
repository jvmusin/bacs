package istu.bacs.problem;

import java.util.List;

public interface ProblemService {
    Problem findById(String problemId);
    void save(Problem problem);
	void saveAll(List<Problem> problems);
}