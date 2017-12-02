package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.repository.ContestRepository;
import istu.bacs.service.ContestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;

    public ContestServiceImpl(ContestRepository contestRepository) {
		this.contestRepository = contestRepository;
    }

	@Override
	public Contest findById(Integer id) {
        return contestRepository.findById(id).orElse(null);
    }
	
	@Override
	public List<Contest> findAll() {
        return contestRepository.findAll();
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