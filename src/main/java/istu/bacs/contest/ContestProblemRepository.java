package istu.bacs.contest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestProblemRepository extends JpaRepository<ContestProblem, Integer> {
    ContestProblem findByContestAndProblemIndex(Contest contest, String problemIndex);
    List<ContestProblem> findAllByContest(Contest contest);
}