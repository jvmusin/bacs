package istu.bacs.web.contest;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.contest.ContestRepository;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ProblemId;
import istu.bacs.db.problem.ProblemRepository;
import istu.bacs.db.problem.ResourceName;
import istu.bacs.web.model.WebModelUtils;
import istu.bacs.web.model.contest.builder.EditContest;
import istu.bacs.web.model.contest.builder.EditContestProblem;
import istu.bacs.web.model.problem.ArchiveProblemId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final EntityManager em;

    @Override
    @Transactional
    public Contest findById(int contestId) {
        return contestRepository.findById(contestId).orElse(null);
    }

    @Override
    public List<Contest> findAll(Pageable pageable) {
        return contestRepository.findAll(pageable).getContent();
    }

    @Override
    public int createContest(EditContest contest) {
        Contest c = Contest.builder()
                .name(contest.getName())
                .startTime(WebModelUtils.parseDateTime(contest.getStartTime()))
                .finishTime(WebModelUtils.parseDateTime(contest.getFinishTime()))
                .problems(new ArrayList<>())
                .build();

        joinProblems(c, contest.getProblems());
        contestRepository.save(c);

        log.info("Contest {}: {} successfully created", c.getContestId(), c.getName());

        return c.getContestId();
    }

    @Override
    @Transactional
    public void editContest(EditContest contest, int contestId) {
        Optional<Contest> realContest = contestRepository.findById(contestId);
        if (!realContest.isPresent())
            throw new IllegalArgumentException("Contest with id " + contestId + " doesn't exist");

        Contest c = realContest.get();
        c.setName(contest.getName());
        c.setStartTime(WebModelUtils.parseDateTime(contest.getStartTime()));
        c.setFinishTime(WebModelUtils.parseDateTime(contest.getFinishTime()));

        //Don't know why but problems are not removing correctly by default
        Session session = em.unwrap(Session.class);
        c.getProblems().forEach(session::delete);

        joinProblems(c, contest.getProblems());
        c.getProblems().forEach(session::save);
    }

    private void joinProblems(Contest contest, List<EditContestProblem> problems) {
        contest.getProblems().clear();
        if (problems == null) return;
        for (EditContestProblem p : problems) {
            ContestProblem problem = parseContestProblem(contest, p);
            contest.getProblems().add(problem);
        }
    }

    private ContestProblem parseContestProblem(Contest contest, EditContestProblem problem) {

        ArchiveProblemId problemId = problem.getProblemId();
        String resourceName = problemId.getResourceName();
        String resourceProblemId = problemId.getResourceProblemId();
        String problemIndex = problem.getProblemIndex();
        //noinspection ConstantConditions
        Problem p = problemRepository.findById(new ProblemId(ResourceName.valueOf(resourceName), resourceProblemId)).get();

        return new ContestProblem().withId(contest, problemIndex).withProblem(p);
    }
}