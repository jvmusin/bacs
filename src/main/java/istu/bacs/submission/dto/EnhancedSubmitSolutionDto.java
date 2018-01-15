package istu.bacs.submission.dto;

import istu.bacs.problem.dto.SubmitSolutionDto;
import istu.bacs.user.User;
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