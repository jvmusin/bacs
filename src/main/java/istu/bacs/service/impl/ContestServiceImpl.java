package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.repository.ContestRepository;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;
	private final ProblemService problemService;

    public ContestServiceImpl(ContestRepository contestRepository, ProblemService problemService) {
		this.contestRepository = contestRepository;
        this.problemService = problemService;
    }

	@Override
	public Contest findById(int id) {
        return contestRepository.findById(id)
                .map(this::prepareContest)
                .orElse(null);
    }
	
	@Override
	public List<Contest> findAll() {
        List<Contest> contests = contestRepository.findAll();
        contests.forEach(this::prepareContest);
        return contests;
    }

    private Contest prepareContest(Contest contest) {
        problemService.updateProblems(contest.getProblems());
        return contest;
    }

    @Override
    public void save(Contest contest) {
        contestRepository.save(contest);
    }

    @Override
    public void delete(Contest contest) {
        contestRepository.delete(contest);
    }
}