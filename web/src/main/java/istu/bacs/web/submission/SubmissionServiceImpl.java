package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.db.user.User;
import istu.bacs.rabbit.RabbitService;
import istu.bacs.web.model.submission.SubmitSolution;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static istu.bacs.db.submission.Verdict.SCHEDULED;
import static istu.bacs.rabbit.QueueName.SCHEDULED_SUBMISSIONS;

@Service
@AllArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final RabbitService rabbitService;

    @Override
    @Transactional
    public Submission findById(int submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElse(null);
        initializeSubmission(submission);
        return submission;
    }

    @Override
    public int submit(SubmitSolution sol, User author) {
        Contest contest = new Contest().withContestId(sol.getContestId());
        SubmissionResult res = new SubmissionResult().withVerdict(SCHEDULED);
        istu.bacs.db.submission.Submission submission = istu.bacs.db.submission.Submission.builder()
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