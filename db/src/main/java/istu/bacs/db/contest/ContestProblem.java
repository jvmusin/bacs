package istu.bacs.db.contest;

import istu.bacs.db.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblem {

    @Id
    @GeneratedValue
    private Integer contestProblemId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String problemIndex;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        ContestProblem contestProblem = (ContestProblem) other;
        return Objects.equals(contestProblemId, contestProblem.contestProblemId);
    }

    @Override
    public int hashCode() {
        return contestProblemId;
    }

    @Override
    public String toString() {
        return "ContestProblem{" +
                "contestProblemId=" + contestProblemId +
                ", problem=" + problem +
                ", problemIndex='" + problemIndex + '\'' +
                '}';
    }
}