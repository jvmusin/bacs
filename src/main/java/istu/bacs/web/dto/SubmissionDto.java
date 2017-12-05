package istu.bacs.web.dto;

import istu.bacs.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data @NoArgsConstructor
public class SubmissionDto {

    private static final String TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private int id;
    private String created;
    private String author;
    private ProblemDto problem;
    private Language language;
    private Verdict verdict;
    private Integer firstFailedTest;
    private Integer usedMillis;
    private Integer usedKilobytes;

    private String solution;

    public SubmissionDto(Submission sub) {
        Problem problem = sub.getProblem();
        Contest contest = sub.getContest();
        SubmissionResult result = sub.getResult();

        this.id = sub.getSubmissionId();
        this.created = formatter.format(sub.getCreationTime());
        this.author = sub.getAuthor().getUsername();
        this.problem = new ProblemDto(contest.getContestId(), contest.getProblemIndex(problem), problem);
        this.language = sub.getLanguage();
        this.verdict = result.getVerdict();
        this.firstFailedTest = result.getTestsPassed();
        this.usedMillis = result.getTimeUsedMillis();
        this.usedKilobytes = result.getMemoryUsedBytes();
        this.solution = sub.getSolution();

        if (firstFailedTest != null) firstFailedTest++;
        if (usedKilobytes != null) usedKilobytes /= 1024;
    }

    public String getSubmissionUrl() {
        return "/submission/" + id;
    }
}