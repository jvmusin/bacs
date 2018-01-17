package istu.bacs.web.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;

import java.util.List;

public interface SubmissionService {
    Submission findById(int submissionId);
    List<Submission> findAll();
    List<Submission> findAllByContest(int contestId);
    List<Submission> findAllByContestAndAuthor(int contestId, int authorUserId);

    int submit(EnhancedSubmitSolutionDto submission);
}