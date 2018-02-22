package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ResourceName;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemService {

    List<Problem> findAll(ResourceName resourceName,
                          String resourceProblemId,
                          Pageable pageable);

    Problem findByContestIdAndProblemIndex(int contestId,
                                           String problemIndex);

    void fetchExternalProblems();
}