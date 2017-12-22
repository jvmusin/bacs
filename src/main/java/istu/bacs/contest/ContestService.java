package istu.bacs.contest;

import istu.bacs.contest.Contest;

import java.util.List;

public interface ContestService {
	
	Contest findById(int id);
	List<Contest> findAll();
	void save(Contest contest);
    void delete(Contest contest);

}