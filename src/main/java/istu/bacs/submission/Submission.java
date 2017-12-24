package istu.bacs.submission;

import istu.bacs.contest.Contest;
import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemConverter;
import istu.bacs.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Integer submissionId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @Column(name = "problem_id")
    @Convert(converter = ProblemConverter.class)
    private Problem problem;

    private boolean pretestsOnly;
    private LocalDateTime creationTime;
    private Language language;
    private String solution;

    private String externalSubmissionId;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private SubmissionResult result;

    public Verdict getVerdict() {
        if (result == null) return Verdict.PENDING;
        return result.getVerdict();
    }
}