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
@EqualsAndHashCode(of = {"contest", "problemIndex"})
public class ContestProblem {

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
}