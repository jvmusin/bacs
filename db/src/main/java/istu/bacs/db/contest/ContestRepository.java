package istu.bacs.db.contest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    Page<Contest> findAll(Pageable pageable);
}