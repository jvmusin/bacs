package istu.bacs.service;

import istu.bacs.domain.Contest;

import java.util.List;

public interface ContestService {
	
	Contest findById(int id);
	List<Contest> findAll();
	void save(Contest contest);
    void delete(Contest contest);

}