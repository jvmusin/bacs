package istu.bacs.service;

import istu.bacs.model.Contest;

public interface ContestService {
	
	Contest findById(Integer id);
	Contest[] findAll();
	void save(Contest contest);
	
}