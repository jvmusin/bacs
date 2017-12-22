package istu.bacs.contest;

import java.util.List;

public interface ContestService {
    Contest findById(int contestId);
    List<Contest> findAll();
    void save(Contest contest);
}