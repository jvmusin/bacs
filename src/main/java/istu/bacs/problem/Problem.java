package istu.bacs.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {

    @Id
    private String problemId;

    private String problemName;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;

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