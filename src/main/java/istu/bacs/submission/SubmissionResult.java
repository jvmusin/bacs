package istu.bacs.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResult {

    @Id
    @GeneratedValue
    private Integer submissionResultId;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    private String buildInfo;

    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;

    public static SubmissionResult withVerdict(Submission submission, Verdict verdict) {
        return SubmissionResult.builder()
                .submission(submission)
                .verdict(verdict)
                .build();
    }
}