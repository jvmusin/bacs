package istu.bacs.web.submission.dto;

import istu.bacs.db.user.User;
import istu.bacs.web.problem.dto.SubmitSolutionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnhancedSubmitSolutionDto {
    private int contestId;
    private String problemIndex;
    private User author;
    private SubmitSolutionDto submission;
}