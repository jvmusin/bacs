package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import istu.bacs.web.model.problem.ContestProblem;
import istu.bacs.web.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    private int id;
    private User author;
    private ContestProblem problem;
    private String created;
    private Language language;
    private String solution;
    private SubmissionResult result;

    public static Submission fromDb(istu.bacs.db.submission.Submission submission) {
        return new Submission(
                submission.getSubmissionId(),
                User.fromDb(submission.getAuthor()),
                ContestProblem.fromDb(submission.getContest().getProblem(submission.getProblemIndex())),
                formatDateTime(submission.getCreated()),
                submission.getLanguage(),
                submission.getSolution(),
                SubmissionResult.fromDb(submission.getResult())
        );
    }
}