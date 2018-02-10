package istu.bacs.db.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResult {

    private String buildInfo;

    @Wither
    @Enumerated(STRING)
    @Column(columnDefinition = "verdict")
    @Type(type = "pgsql_enum")
    private Verdict verdict;
    private Integer testsPassed;
    private Integer timeUsedMillis;
    private Integer memoryUsedBytes;
}