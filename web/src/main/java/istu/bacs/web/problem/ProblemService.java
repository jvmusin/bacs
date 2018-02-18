package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll(Pageable pageable);
    Problem findById(String problemId);
    void saveAll(Iterable<Problem> problems);
}