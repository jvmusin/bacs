package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.contest.ContestProblemRepository;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.user.User;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static istu.bacs.db.submission.Verdict.NOT_SUBMITTED;
import static istu.bacs.rabbit.QueueNames.SCHEDULED_SUBMISSIONS;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ContestProblemRepository contestProblemRepository;
    private final RabbitTemplate rabbitTemplate;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ContestProblemRepository contestProblemRepository, RabbitTemplate rabbitTemplate) {
        this.submissionRepository = submissionRepository;
        this.contestProblemRepository = contestProblemRepository;
        this.rabbitTemplate = rabbitTemplate;
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
        SubmissionResult result = new SubmissionResult();

        result.setVerdict(NOT_SUBMITTED);
        result.setSubmission(sub);
        sub.setResult(result);

        submissionRepository.save(sub);
        rabbitTemplate.convertAndSend(SCHEDULED_SUBMISSIONS, sub.getSubmissionId());
        return sub.getSubmissionId();
    }
}