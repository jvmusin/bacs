package istu.bacs.service.impl;

import istu.bacs.model.Problem;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProblemServiceImpl implements ProblemService {
	
	private final Map<Integer, Problem> problemById = new HashMap<>();
	
	@Override
	public Problem findById(Integer id) {
		return problemById.get(id);
	}
	
	@Override
	public void save(Problem problem) {
		problemById.put(problem.getProblemId(), problem);
	}
	
}