package istu.bacs.db.contest;

import istu.bacs.db.problem.Problem;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    public static String createId(int contestId, String problemIndex) {
        return contestId + "#" + problemIndex;
    }

    public static ContestProblem withContestIdAndProblemIndex(int contestId, String problemIndex) {
        return ContestProblem.builder()
                .contestProblemId(createId(contestId, problemIndex))
                .contest(Contest.builder().contestId(contestId).build())
                .problemIndex(problemIndex)
                .build();
    }
}