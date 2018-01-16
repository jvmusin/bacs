package istu.bacs.web.problem.dto;

import istu.bacs.db.submission.Language;
import lombok.Data;

@Data
public class SubmitSolutionDto {
    private Language language;
    private String solution;
}