package istu.bacs.web.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.model.submission.SubmitSolution;

public interface SubmissionService {
    Submission findById(int submissionId);

    int submit(SubmitSolution sol, User author);
}