package istu.bacs.db.contest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contest {

    @Id
    @GeneratedValue
    private Integer contestId;

    private String contestName;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @OneToMany(cascade = ALL, mappedBy = "contest", fetch = LAZY)
    @OrderBy("problemIndex ASC")
    private List<ContestProblem> problems;

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