package istu.bacs.db.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        Problem problem = (Problem) other;
        return Objects.equals(problemId, problem.problemId);
    }

    @Override
    public int hashCode() {
        return problemId.hashCode();
    }
}