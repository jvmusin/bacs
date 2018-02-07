package istu.bacs.web.submission;

import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.user.User;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.web.model.post.SubmitSolution;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static istu.bacs.db.submission.Verdict.SCHEDULED;
import static istu.bacs.rabbit.QueueName.SCHEDULED_SUBMISSIONS;

@Service
@AllArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final RabbitService rabbitService;

    @Override
    public Submission findById(int submissionId) {
        return submissionRepository.findById(submissionId).orElse(null);
    }

    @Override
    public int submit(SubmitSolution sol, User author) {
        SubmissionResult res = new SubmissionResult().withVerdict(SCHEDULED);
        ContestProblem cp = ContestProblem.withId(sol.getContestId(), sol.getProblemIndex());
        istu.bacs.db.submission.Submission submission = istu.bacs.db.submission.Submission.builder()
                .author(author)
                .contestProblem(cp)
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
}