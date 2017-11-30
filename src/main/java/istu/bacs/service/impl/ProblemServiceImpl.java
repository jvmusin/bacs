package istu.bacs.service.impl;

import istu.bacs.model.Problem;
import istu.bacs.repository.ProblemRepository;
import istu.bacs.service.ProblemService;
import istu.bacs.sybon.SybonApi;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;
    private final SybonApi sybon;

	public ProblemServiceImpl(ProblemRepository problemRepository, SybonApi sybon) {
		this.problemRepository = problemRepository;
        this.sybon = sybon;
    }
	
	@Override
	public Problem findById(Integer id) {
        return problemRepository.findById(id)
                .map(p -> sybon.getProblem(p.getProblemId()))
                .orElseGet(() -> {
                    Problem p = sybon.getProblem(id);
                    problemRepository.save(p);
                    return p;
                });
    }
	
	@Override
	public void save(Problem problem) {
	    problemRepository.save(problem);
	}
}