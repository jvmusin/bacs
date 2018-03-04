package istu.bacs.web.model.contest.builder;

import istu.bacs.web.model.problem.ArchiveProblemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditContestProblem {
    private ArchiveProblemId problemId;
    private String index;
}