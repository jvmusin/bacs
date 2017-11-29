package istu.bacs.service.impl;

import istu.bacs.model.Problem;
import istu.bacs.repository.ProblemRepository;
import istu.bacs.service.ProblemService;
import istu.bacs.sybon.SybonApi;
import istu.bacs.sybon.converter.SybonProblemConverter;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;
	private final SybonProblemConverter sybonProblemConverter;
	private final SybonApi sybon;

	public ProblemServiceImpl(ProblemRepository problemRepository, SybonProblemConverter sybonProblemConverter, SybonApi sybon) {
		this.problemRepository = problemRepository;
        this.sybonProblemConverter = sybonProblemConverter;
        this.sybon = sybon;
    }
	
	@Override
	public Problem findById(Integer id) {
	    return sybonProblemConverter.convert(sybon.getProblem(id));
	}
	
	@Override
	public void save(Problem problem) {
	    problemRepository.save(problem);
	}
}