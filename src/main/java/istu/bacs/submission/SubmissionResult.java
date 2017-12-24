package istu.bacs.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import static istu.bacs.submission.Verdict.PENDING;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResult {

    @Id
    private Integer submissionId;

    private boolean built;
    private String buildInfo;

    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;

    public static SubmissionResult pending(Integer submissionId) {
        return SubmissionResult.builder()
                .submissionId(submissionId)
                .verdict(PENDING)
                .build();
    }
}