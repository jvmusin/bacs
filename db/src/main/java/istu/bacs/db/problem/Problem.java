package istu.bacs.db.problem;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "problemId")
public class Problem {

    @Id
    private String problemId;

    private String name;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;

    public String getResourceName() {
        return problemId.split("@")[0];
    }

    public String getRawProblemName() {
        return problemId.split("@")[1];
    }
}