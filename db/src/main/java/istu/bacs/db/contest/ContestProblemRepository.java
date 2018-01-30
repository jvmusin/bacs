package istu.bacs.db.contest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestProblemRepository extends JpaRepository<ContestProblem, String> {
}