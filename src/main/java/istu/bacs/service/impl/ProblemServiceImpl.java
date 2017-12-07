package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Problem;
import istu.bacs.repository.ProblemRepository;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;
    private final ExternalApiAggregator externalApi;

    private final Map<String, Problem> knownProblems = new ConcurrentHashMap<>();

    public ProblemServiceImpl(ProblemRepository problemRepository, ExternalApiAggregator externalApi) {
		this.problemRepository = problemRepository;
        this.externalApi = externalApi;
    }
	
	@Override
	public Problem findById(String problemId) {
        return knownProblems.computeIfAbsent(problemId, id -> {
            Problem problem = new Problem();
            problem.setProblemId(id);
            externalApi.updateProblemDetails(singletonList(problem));
            return problem;
        });
    }

    @Override
    public List<Problem> findAll() {
        List<Problem> existingProblems = problemRepository.findAll();
        updateProblems(existingProblems);
        return existingProblems;
    }

    @Override
    public void updateProblems(List<Problem> problems) {
        List<Problem> unknownProblems = problems.stream()
                .filter(p -> !knownProblems.containsKey(p.getProblemId()))
                .collect(toList());
        externalApi.updateProblemDetails(unknownProblems);
        unknownProblems.forEach(p -> knownProblems.put(p.getProblemId(), p));
        problems.forEach(p -> p.setDetails(findById(p.getProblemId()).getDetails()));
    }

    @Override
	public void save(Problem problem) {
        knownProblems.put(problem.getProblemId(), problem);
	    problemRepository.save(problem);
	}

    @Override
    public void saveAll(List<Problem> problems) {
        problems.forEach(p -> knownProblems.put(p.getProblemId(), p));
        problemRepository.saveAll(problems);
    }
}