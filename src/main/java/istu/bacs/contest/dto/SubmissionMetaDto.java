package istu.bacs.contest.dto;

import istu.bacs.contest.Contest;
import istu.bacs.problem.Problem;
import istu.bacs.submission.Language;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionResult;
import istu.bacs.submission.Verdict;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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

        Contest contest0 = submission.getContest();

        contest = new ContestMetaDto(contest0);
        problem = new ProblemDto(submission.getProblem(), getProblemIndex(contest0, submission.getProblem()));
        author = new UserDto(submission.getAuthor());

        created = submission.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME);
        language = submission.getLanguage();
        verdict = submission.getVerdict();

        SubmissionResult result = submission.getResult();
        testsPassed = result.getTestsPassed();
        timeUsedMillis = result.getTimeUsedMillis();
        memoryUsedBytes = result.getMemoryUsedBytes();
    }

    private int getProblemIndex(Contest contest, Problem problem) {
        List<Problem> problems = contest.getProblems();
        for (int i = 0; i < problems.size(); i++)
            if (Objects.equals(problems.get(i).getProblemId(), problem.getProblemId()))
                return i;
        return -1;
    }
}