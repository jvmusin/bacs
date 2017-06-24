package istu.bacs.service.impl;

import istu.bacs.model.Problem;
import istu.bacs.repository.ProblemRepository;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {
	
	private final ProblemRepository problemRepository;
	
	public ProblemServiceImpl(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}
	
	@Override
	public Problem findById(Integer id) {
		return problemRepository.findById(id).orElse(null);
	}
	
	@Override
	public void save(Problem problem) {
		problemRepository.save(problem);
	}
	
}