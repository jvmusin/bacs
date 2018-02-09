package istu.bacs.db.problem;

import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "problem", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "problemId")
public class Problem {

    private static final String ID_DELIMITER = "#";

    @Id
    @Wither
    private String problemId;

    private String name;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;

    public String getResourceName() {
        return problemId.split(ID_DELIMITER)[0];
    }

    public String getRawProblemId() {
        return problemId.split(ID_DELIMITER)[1];
    }

    public Problem withId(String resourceName, String resourceProblemId) {
        return new Problem().withProblemId(resourceName + ID_DELIMITER + resourceProblemId);
    }
}