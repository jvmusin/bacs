package istu.bacs.submission;

import istu.bacs.contest.Contest;
import istu.bacs.user.User;
import istu.bacs.problem.Problem;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data @Entity
public class Submission {

    @Id @GeneratedValue
    private Integer submissionId;

    @ManyToOne @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne @JoinColumn(name = "contest_id")
    private Contest contest;
    @Column(name = "problem_id")
    private Problem problem;

    private boolean pretestsOnly;
    private LocalDateTime creationTime;
    private Language language;
    private String solution;

    private String externalSubmissionId;

    @Transient
    private SubmissionResult result;

    public Verdict getVerdict() {
        if (result == null) return Verdict.PENDING;
        return result.getVerdict();
    }
}