package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.user.User;
import istu.bacs.web.contest.ContestService;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static istu.bacs.db.submission.Verdict.SCHEDULED;
import static istu.bacs.rabbit.QueueNames.SCHEDULED_SUBMISSIONS;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ContestService contestService;
    private final RabbitTemplate rabbitTemplate;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, ContestService contestService, RabbitTemplate rabbitTemplate) {
        this.submissionRepository = submissionRepository;
        this.contestService = contestService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
    }

    @Override
    public List<Submission> findAllByContest(int contestId, Pageable pageable) {
        Contest contest = contestService.findById(contestId);
        List<ContestProblem> problems = contest.getProblems();
        return submissionRepository.findAllByContestProblem(problems, pageable);
    }

    @Override
    public List<Submission> findAllByContestAndAuthor(int contestId, User author, Pageable pageable) {
        Contest contest = contestService.findById(contestId);
        List<ContestProblem> problems = contest.getProblems();
        return submissionRepository.findAllByAuthorAndContestProblem(author, problems, pageable);
    }

    @Override
    public int submit(EnhancedSubmitSolutionDto submission) {
        LocalDateTime now = LocalDateTime.now();

        Contest contest = contestService.findById(submission.getContestId());
        ContestProblem contestProblem = contest.getProblem(submission.getProblemIndex());

        Submission sub = Submission.builder()
                .author(submission.getAuthor())
                .contestProblem(contestProblem)
                .pretestsOnly(false)
                .created(now)
                .language(submission.getSubmission().getLanguage())
                .solution(submission.getSubmission().getSolution())
                .build();
        SubmissionResult result = new SubmissionResult();

        result.setVerdict(SCHEDULED);
        result.setSubmission(sub);
        sub.setResult(result);

        submissionRepository.save(sub);
        rabbitTemplate.convertAndSend(SCHEDULED_SUBMISSIONS, sub.getSubmissionId());
        return sub.getSubmissionId();
    }
}