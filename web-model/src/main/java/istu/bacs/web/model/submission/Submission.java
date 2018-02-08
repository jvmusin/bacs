package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import istu.bacs.web.model.problem.ContestProblem;
import istu.bacs.web.model.user.User;
import lombok.Value;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Submission {
    int id;
    User author;
    ContestProblem problem;
    String created;
    Language language;
    String solution;
    SubmissionResult result;

    public static Submission fromDb(istu.bacs.db.submission.Submission submission) {
        return new Submission(
                submission.getSubmissionId(),
                User.fromDb(submission.getAuthor()),
                ContestProblem.fromDb(submission.getContestProblem()),
                formatDateTime(submission.getCreated()),
                submission.getLanguage(),
                submission.getSolution(),
                SubmissionResult.fromDb(submission.getResult())
        );
    }
}