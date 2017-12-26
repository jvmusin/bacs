package istu.bacs.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static istu.bacs.submission.Verdict.PENDING;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResult {

    @Id
    @GeneratedValue
    private Integer submissionResultId;

    @OneToOne(mappedBy = "result")
    private Submission submission;

    private String buildInfo;

    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;

    public static SubmissionResult pending(Submission submission) {
        return SubmissionResult.builder()
                .submission(submission)
                .verdict(PENDING)
                .build();
    }
}