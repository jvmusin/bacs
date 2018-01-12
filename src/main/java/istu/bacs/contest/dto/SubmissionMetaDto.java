package istu.bacs.contest.dto;

import istu.bacs.submission.Language;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionResult;
import istu.bacs.submission.Verdict;
import lombok.Data;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Data
public class SubmissionMetaDto {

    private int id;

    private ContestMetaDto contest;
    private ProblemDto problem;
    private UserDto author;

    private String created;
    private Language language;
    private Verdict verdict;

    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;

    public SubmissionMetaDto(Submission submission) {
        id = submission.getSubmissionId();

        contest = new ContestMetaDto(submission.getContest());
        problem = new ProblemDto(submission.getContestProblem());
        author = new UserDto(submission.getAuthor());

        created = submission.getCreated().format(ISO_DATE_TIME);
        language = submission.getLanguage();
        verdict = submission.getVerdict();

        SubmissionResult result = submission.getResult();
        testsPassed = result.getTestsPassed();
        timeUsedMillis = result.getTimeUsedMillis();
        memoryUsedBytes = result.getMemoryUsedBytes();
    }
}