package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.user.User;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.web.contest.ContestService;
import istu.bacs.web.model.submission.SubmitSolution;
import istu.bacs.web.user.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static istu.bacs.db.submission.Verdict.SCHEDULED;
import static istu.bacs.rabbit.QueueName.SCHEDULED_SUBMISSIONS;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ContestService contestService;
    private final UserService userService;
    private final EntityManager em;
    private final RabbitService rabbitService;

    @Override
    @Transactional
    public Submission findById(int submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElse(null);
        initializeSubmission(submission);
        return submission;
    }

    @Override
    public List<Submission> findAll(Integer contestId,
                                    String problemIndex,
                                    String authorUsername) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Submission> query = cb.createQuery(Submission.class);
        Root<Submission> s = query.from(Submission.class);
        s.fetch("contest");

        List<Predicate> predicates = new ArrayList<>();
        if (authorUsername != null) {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User author = authorUsername.equals(currentUser.getUsername())
                    ? currentUser
                    : userService.findByUsername(authorUsername);
            predicates.add(cb.equal(s.get("author"), author));
        }
        if (contestId != null) {
            List<ContestProblem> problems = contestService.findById(contestId).getProblems()
                    .stream()
                    .filter(p -> problemIndex == null || p.getProblemIndex().equals(problemIndex))
                    .collect(toList());
            predicates.add(s.get("contestProblem").in(problems));
        }

        query.select(s)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(new OrderImpl(s.get("submissionId")).reverse());

        return em.createQuery(query).getResultList();
    }

    @Override
    public int submit(SubmitSolution sol, User author) {
        Contest contest = new Contest().withContestId(sol.getContestId());
        SubmissionResult res = new SubmissionResult().withVerdict(SCHEDULED);
        Submission submission = Submission.builder()
                .author(author)
                .contest(contest)
                .problemIndex(sol.getProblemIndex())
                .pretestsOnly(false)
                .created(LocalDateTime.now())
                .language(sol.getLanguage())
                .solution(sol.getSolution())
                .result(res)
                .build();
        submissionRepository.save(submission);
        rabbitService.send(SCHEDULED_SUBMISSIONS, submission.getSubmissionId());
        return submission.getSubmissionId();
    }

    private void initializeSubmission(Submission submission) {
        if (submission != null)
            //noinspection ResultOfMethodCallIgnored
            submission.getContest();
    }
}