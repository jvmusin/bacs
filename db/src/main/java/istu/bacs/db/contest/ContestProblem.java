package istu.bacs.db.contest;

import istu.bacs.db.problem.Problem;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "contest")
@EqualsAndHashCode(of = {"contest", "problemIndex"})
public class ContestProblem implements Serializable {

    @ManyToOne
    @JoinColumn(name = "contest_id")
    @Id
    private Contest contest;

    @Id
    private String problemIndex;

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "resource_name"),
            @JoinColumn(name = "resource_problem_id")
    })
    private Problem problem;

    public ContestProblem withId(int contestId, String problemIndex) {
        setContest(new Contest().withContestId(contestId));
        setProblemIndex(problemIndex);
        return this;
    }
}