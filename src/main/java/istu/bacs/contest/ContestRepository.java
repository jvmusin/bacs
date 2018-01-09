package istu.bacs.contest;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    Page<Contest> findAll(@NotNull Pageable pageable);
    void deleteByContestId(int contestId);
}