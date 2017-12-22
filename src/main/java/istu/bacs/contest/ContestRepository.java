package istu.bacs.contest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    void deleteByContestId(int contestId);
}