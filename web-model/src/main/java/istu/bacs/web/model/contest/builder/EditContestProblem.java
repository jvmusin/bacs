package istu.bacs.web.model.contest.builder;

import istu.bacs.web.model.problem.ArchiveProblemId;
import lombok.Value;

@Value
public class EditContestProblem {
    ArchiveProblemId problemId;
    String problemIndex;
}