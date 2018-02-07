package istu.bacs.db.contest;

import istu.bacs.db.problem.Problem;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "contest")
@EqualsAndHashCode(of = "contestProblemId")
public class ContestProblem {

    @Id
    private String contestProblemId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String problemIndex;

    public static ContestProblem withId(int contestId, String problemIndex) {
        ContestProblem cp = new ContestProblem();
        cp.setContestProblemId(contestId + "#" + problemIndex);
        return cp;
    }
}