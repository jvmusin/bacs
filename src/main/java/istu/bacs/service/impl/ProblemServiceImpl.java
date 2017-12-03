package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Problem;
import istu.bacs.repository.ProblemRepository;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;
    private final ExternalApiAggregator externalApi;

	public ProblemServiceImpl(ProblemRepository problemRepository, ExternalApiAggregator externalApi) {
		this.problemRepository = problemRepository;
        this.externalApi = externalApi;
    }
	
	@Override
	public Problem findById(String problemId) {
	    return externalApi.getProblem(problemId);
    }

    @Override
    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    @Override
	public void save(Problem problem) {
	    problemRepository.save(problem);
	}

    @Override
    public void saveAll(Iterable<Problem> problems) {
        problemRepository.saveAll(problems);
    }
}