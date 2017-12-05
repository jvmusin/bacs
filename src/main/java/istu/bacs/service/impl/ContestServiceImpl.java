package istu.bacs.service.impl;

import istu.bacs.externalapi.ExternalApiAggregator;
import istu.bacs.model.Contest;
import istu.bacs.repository.ContestRepository;
import istu.bacs.service.ContestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;
	private final ExternalApiAggregator externalApi;

    public ContestServiceImpl(ContestRepository contestRepository, ExternalApiAggregator externalApi) {
		this.contestRepository = contestRepository;
        this.externalApi = externalApi;
    }

	@Override
	public Contest findById(int id) {
        return contestRepository.findById(id)
                .map(contest -> {
                    externalApi.updateProblems(contest.getProblems());
                    return contest;
                })
                .orElse(null);
    }
	
	@Override
	public List<Contest> findAll() {
        List<Contest> contests = contestRepository.findAll();
        contests.forEach(c -> externalApi.updateProblems(c.getProblems()));
        return contests;
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