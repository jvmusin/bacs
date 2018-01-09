package istu.bacs.contest;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContestService {
    Contest findById(int contestId);
    List<Contest> findAll(Pageable pageable);
    void save(Contest contest);
}