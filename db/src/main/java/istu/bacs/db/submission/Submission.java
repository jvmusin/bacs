package istu.bacs.db.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.problem.Problem;
import istu.bacs.db.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "submission", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "submissionId")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submissionId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String problemIndex;

    private boolean pretestsOnly;
    private LocalDateTime created;
    @Enumerated(STRING)
    private Language language;
    private String solution;

    private Integer externalSubmissionId;

    @Embedded
    private SubmissionResult result;

    public Verdict getVerdict() {
        return getResult().getVerdict();
    }

    public ContestProblem getContestProblem() {
        return getContest().getProblem(getProblemIndex());
    }

    public Problem getProblem() {
        return getContestProblem().getProblem();
    }
}