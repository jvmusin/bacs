package istu.bacs.service.impl;

import istu.bacs.model.Problem;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final Map<String, Problem> cache = new ConcurrentHashMap<>();

	@Override
	public Problem findById(String problemId) {
        return cache.get(problemId);
    }

    @Override
    public List<Problem> findAll() {
        return cache.values().stream()
                .sorted()
                .collect(toList());
    }

    @Override
	public void save(Problem problem) {
        cache.put(problem.getProblemId(), problem);
	}

    @Override
    public void saveAll(List<Problem> problems) {
        problems.forEach(this::save);
    }
}