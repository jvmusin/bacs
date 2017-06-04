package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.service.ContestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestServiceImpl implements ContestService {
	
	private final Map<Integer, Contest> contestById = new HashMap<>();
	
	@Override
	public Contest findById(Integer id) {
		return contestById.get(id);
	}
	
	@Override
	public List<Contest> findAll() {
		return new ArrayList<>(contestById.values());
	}
	
	@Override
	public void save(Contest contest) {
		contestById.put(contest.getContestId(), contest);
	}
	
}