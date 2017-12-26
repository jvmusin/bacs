package istu.bacs.contest;

import istu.bacs.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contest {

    @Id
    @GeneratedValue
    private Integer contestId;

    private String contestName;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @ManyToMany
    @OrderColumn(name = "problem_index")
    @JoinTable(name = "contest_problems",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id"))
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