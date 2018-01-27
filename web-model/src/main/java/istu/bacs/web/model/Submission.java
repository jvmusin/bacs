package istu.bacs.web.model;

import istu.bacs.db.submission.Language;
import lombok.Value;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Submission {
    int id;
    User author;
    Problem problem;
    String created;
    Language language;
    String solution;
    SubmissionResult result;

    public static Submission fromDb(istu.bacs.db.submission.Submission submission) {
        return new Submission(
                submission.getSubmissionId(),
                User.fromDb(submission.getAuthor()),
                Problem.fromDb(submission.getContestProblem()),
                formatDateTime(submission.getCreated()),
                submission.getLanguage(),
                submission.getSolution(),
                SubmissionResult.fromDb(submission.getResult())
        );
    }
}