package istu.bacs.web.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.user.User;
import istu.bacs.web.contest.ContestService;
import istu.bacs.web.model.get.Submission;
import istu.bacs.web.model.post.SubmitSolution;
import lombok.AllArgsConstructor;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final ContestService contestService;
    private final EntityManager em;

    @GetMapping("/{submissionId}")
    public Submission getSubmission(@PathVariable int submissionId) {
        return Submission.fromDb(submissionService.findById(submissionId));
    }

    @GetMapping
    public List<Submission> getSubmissions(
            @RequestParam(name = "contestId", required = false) Integer contestId,
            @RequestParam(name = "problemIndex", required = false) String problemIndex,
            @RequestParam(name = "author", required = false) String author) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<istu.bacs.db.submission.Submission> query = cb.createQuery(istu.bacs.db.submission.Submission.class);
        Root<istu.bacs.db.submission.Submission> s = query.from(istu.bacs.db.submission.Submission.class);

        List<Predicate> predicates = new ArrayList<>();
        if (author != null) {
            predicates.add(cb.equal(s.get("author"), new User().withUsername(author)));
        }
        if (contestId != null) {
            List<ContestProblem> problems = contestService.findById(contestId).getProblems()
                    .stream()
                    .filter(p -> problemIndex == null || p.getProblemIndex().equals(problemIndex))
                    .collect(toList());
            predicates.add(s.get("contestProblem").in(problems));
        }

        query.select(s)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(new OrderImpl(s.get("submissionId"), false));

        return em.createQuery(query).getResultList()
                .stream()
                .map(Submission::fromDb)
                .collect(toList());
    }

    @PostMapping
    public int submitSolution(@RequestBody SubmitSolution sol, @AuthenticationPrincipal User author) {
        return submissionService.submit(sol, author);
    }
}