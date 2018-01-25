package istu.bacs.db.submission;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "submission")
public class SubmissionResult {

    @Id
    @GeneratedValue
    private Integer submissionResultId;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    private String buildInfo;

    @Enumerated(STRING)
    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}