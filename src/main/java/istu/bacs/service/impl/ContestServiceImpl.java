package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.repository.ContestRepository;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.sybon.SybonApi;
import istu.bacs.sybon.SybonProblemCollection;
import istu.bacs.sybon.converter.SybonProblemConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;
	private final ProblemService problemService;
    private final SybonProblemConverter sybonProblemConverter;
    private final SybonApi sybon;

	public ContestServiceImpl(ContestRepository contestRepository, ProblemService problemService, SybonProblemConverter sybonProblemConverter, SybonApi sybon) {
		this.contestRepository = contestRepository;
        this.problemService = problemService;
        this.sybonProblemConverter = sybonProblemConverter;
        this.sybon = sybon;
    }

	@Override
	public Contest findById(Integer id) {
        SybonProblemCollection problemCollection = sybon.getProblemCollection(1);
        Problem[] problems = Arrays.stream(problemCollection.getProblems())
                .map(sybonProblemConverter::convert)
                .toArray(Problem[]::new);
        return new Contest(problemCollection.getId(), problemCollection.getName(),
                LocalDateTime.now(), LocalDateTime.now(), problems);
	}
	
	@Override
	public Contest[] findAll() {
        return new Contest[]{findById(null)};
    }
	
	@Override
	public void save(Contest contest) {
	}
}