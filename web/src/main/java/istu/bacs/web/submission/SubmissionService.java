package istu.bacs.web.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.model.submission.SubmitSolution;

import java.util.List;

public interface SubmissionService {

    Submission findById(int submissionId);

    List<Submission> findAll(Integer contestId,
                             String problemIndex,
                             String authorUsername);

    int submit(SubmitSolution sol, User author);
}