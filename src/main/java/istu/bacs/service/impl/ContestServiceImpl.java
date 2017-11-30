package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.repository.ContestRepository;
import istu.bacs.service.ContestService;
import istu.bacs.sybon.SybonApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;
    private final SybonApi sybon;

	public ContestServiceImpl(ContestRepository contestRepository, SybonApi sybon) {
		this.contestRepository = contestRepository;
        this.sybon = sybon;
    }

	@Override
	public Contest findById(Integer id) {
        return contestRepository.findById(id)
                .map(c -> {
                    Problem[] problems = c.getProblems();
                    for (int i = 0; i < problems.length; i++)
                        problems[i] = sybon.getProblem(problems[i].getProblemId());
                    return c;
                }).orElse(null);
    }
	
	@Override
	public List<Contest> findAll() {
        return contestRepository.findAll();
    }
}