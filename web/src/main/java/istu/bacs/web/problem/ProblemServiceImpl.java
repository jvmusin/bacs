package istu.bacs.web.problem;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemRepository;
import istu.bacs.db.problem.ResourceName;
import istu.bacs.externalapi.ExternalApi;
import istu.bacs.web.contest.ContestService;
import lombok.AllArgsConstructor;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final ContestService contestService;
    private final ExternalApi externalApi;
    private final EntityManager em;

    @Override
    public List<Problem> findAll(ResourceName resourceName,
                                 String resourceProblemId,
                                 Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Problem> query = cb.createQuery(Problem.class);
        Root<Problem> s = query.from(Problem.class);

        List<Predicate> predicates = new ArrayList<>();
        if (resourceName != null)
            predicates.add(cb.equal(s.get("problemId").get("resourceName"), resourceName));
        if (resourceProblemId != null)
            predicates.add(cb.equal(s.get("problemId").get("resourceProblemId"), resourceProblemId));

        query.select(s)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(new OrderImpl(s.get("problemId")));

        return em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Problem findByContestIdAndProblemIndex(int contestId, String problemIndex) {
        return contestService.findById(contestId).getProblem(problemIndex).getProblem();
    }

    @Override
    public void fetchExternalProblems() {
        problemRepository.saveAll(externalApi.getAllProblems());
    }
}