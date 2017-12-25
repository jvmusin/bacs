package istu.bacs.contest;

import istu.bacs.problem.Problem;
import istu.bacs.problem.ProblemListConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class Contest {

    @Id
    @GeneratedValue
    private Integer contestId;

    private String contestName;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @Convert(converter = ProblemListConverter.class)
    private List<Problem> problems;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        Contest contest = (Contest) other;
        return Objects.equals(contestId, contest.contestId);
    }

    @Override
    public int hashCode() {
        return contestId;
    }
}