package istu.bacs.db.problem;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "problem", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "problemId")
public class Problem {

    @EmbeddedId
    private ProblemId problemId;

    private String name;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;

    public ResourceName getResourceName() {
        return problemId.getResourceName();
    }

    public Problem withId(String resourceName, String resourceProblemId) {
        ResourceName resource = ResourceName.valueOf(resourceName);
        problemId = new ProblemId(resource, resourceProblemId);
        return this;
    }
}