package istu.bacs.submission;

import istu.bacs.contest.Contest;
import istu.bacs.contest.ContestProblem;
import istu.bacs.contest.ContestProblemRepository;
import istu.bacs.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ContestProblemRepository contestProblemRepository;

    private final List<Consumer<Submission>> onScheduledSubscribers = Collections.synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onSubmittedSubscribers = Collections.synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onTestedSubscribers = Collections.synchronizedList(new ArrayList<>());

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ContestProblemRepository contestProblemRepository) {
        this.submissionRepository = submissionRepository;
        this.contestProblemRepository = contestProblemRepository;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
    }

    @Override
    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> findAllByContest(int contestId) {
        Contest contest = Contest.builder().contestId(contestId).build();
        List<ContestProblem> problems = contestProblemRepository.findAllByContest(contest);
        return submissionRepository.findAllByContestProblems(problems);
    }

    @Override
    public List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId) {
        User author = User.builder().userId(authorUserId).build();
        Contest contest = Contest.builder().contestId(contestId).build();
        List<ContestProblem> problems = contestProblemRepository.findAllByContest(contest);
        return submissionRepository.findAllByAuthorAndContestProblem(author, problems);
    }

    @Override
    public int submit(Submission submission) {
        save(submission);
        solutionScheduled(submission);
        return submission.getSubmissionId();
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }

    @Override
    public void subscribeOnSolutionScheduled(Consumer<Submission> function) {
        onScheduledSubscribers.add(function);
    }

    @Override
    public void solutionScheduled(Submission submission) {
        onScheduledSubscribers.forEach(f -> f.accept(submission));
    }

    @Override
    public void subscribeOnSolutionSubmitted(Consumer<Submission> function) {
        onSubmittedSubscribers.add(function);
    }

    @Override
    public void solutionSubmitted(Submission submission) {
        onSubmittedSubscribers.forEach(f -> f.accept(submission));
    }

    @Override
    public void subscribeOnSolutionTested(Consumer<Submission> function) {
        onTestedSubscribers.add(function);
    }

    @Override
    public void solutionTested(Submission submission) {
        onTestedSubscribers.forEach(f -> f.accept(submission));
    }
}