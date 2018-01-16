package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.contest.ContestProblemRepository;
import istu.bacs.db.submission.*;
import istu.bacs.db.user.User;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static istu.bacs.db.submission.SubmissionResult.withVerdict;
import static istu.bacs.db.submission.Verdict.NOT_SUBMITTED;
import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toList;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;
    private final ContestProblemRepository contestProblemRepository;

    private final List<Consumer<Submission>> onScheduledSubscribers = synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onSubmittedSubscribers = synchronizedList(new ArrayList<>());
    private final List<Consumer<Submission>> onTestedSubscribers = synchronizedList(new ArrayList<>());

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionResultRepository submissionResultRepository, ContestProblemRepository contestProblemRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
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
    public List<Submission> findAllByVerdict(Verdict verdict) {
        return submissionResultRepository.findAllByVerdict(verdict).stream()
                .map(SubmissionResult::getSubmission)
                .collect(toList());
    }

    @Override
    public int submit(EnhancedSubmitSolutionDto submission) {
        LocalDateTime now = LocalDateTime.now();

        Contest contest = Contest.builder().contestId(submission.getContestId()).build();
        ContestProblem contestProblem = contestProblemRepository.findByContestAndProblemIndex(contest, submission.getProblemIndex());

        Submission sub = Submission.builder()
                .author(submission.getAuthor())
                .contestProblem(contestProblem)
                .pretestsOnly(false)
                .created(now)
                .language(submission.getSubmission().getLanguage())
                .solution(submission.getSubmission().getSolution())
                .build();
        sub.setResult(withVerdict(sub, NOT_SUBMITTED));

        save(sub);
        solutionScheduled(sub);
        return sub.getSubmissionId();
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