package istu.bacs.db.problem;

import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "problemId")
public class Problem {

    @Id
    @Wither
    private String problemId;

    private String name;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;

    public String getResourceName() {
        return problemId.split("#")[0];
    }

    public String getRawProblemId() {
        return problemId.split("#")[1];
    }
}