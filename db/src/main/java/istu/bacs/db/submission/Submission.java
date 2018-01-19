package istu.bacs.db.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue
    private Integer submissionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contest_problem_id")
    private ContestProblem contestProblem;

    private boolean pretestsOnly;
    private LocalDateTime created;
    @Enumerated(STRING)
    private Language language;
    private String solution;

    private Integer externalSubmissionId;

    @OneToOne(cascade = ALL, mappedBy = "submission")
    private SubmissionResult result;

    public Verdict getVerdict() {
        return getResult().getVerdict();
    }

    public Contest getContest() {
        return getContestProblem().getContest();
    }

    public Problem getProblem() {
        return getContestProblem().getProblem();
    }

    public String getResourceName() {
        return getProblem().getResourceName();
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