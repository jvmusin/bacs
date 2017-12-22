package istu.bacs.contest;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "contests")
public class ContestServiceImpl implements ContestService {
	
	private final ContestRepository contestRepository;

    public ContestServiceImpl(ContestRepository contestRepository) {
		this.contestRepository = contestRepository;
    }

	@Override
    @Cacheable(key = "#contestId", unless = "#result == null")
	public Contest findById(int contestId) {
        return contestRepository.findById(contestId).orElse(null);
    }
	
	@Override
    @Cacheable(key = "'all'")
	public List<Contest> findAll() {
        return contestRepository.findAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void save(Contest contest) {
        contestRepository.save(contest);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Contest contest) {
        contestRepository.delete(contest);
    }
}