package istu.bacs.submission;

import istu.bacs.contest.Contest;
import istu.bacs.problem.Problem;
import istu.bacs.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static istu.bacs.submission.Verdict.PENDING;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private boolean pretestsOnly;
    private LocalDateTime created;
    private Language language;
    private String solution;

    private String externalSubmissionId;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private SubmissionResult result;

    public Verdict getVerdict() {
        if (result == null) return PENDING;
        return result.getVerdict();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        Submission submission = (Submission) other;
        return Objects.equals(submissionId, submission.submissionId);
    }

    @Override
    public int hashCode() {
        return submissionId;
    }
}