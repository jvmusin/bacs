package istu.bacs.contest.dto;

import istu.bacs.submission.Language;
import lombok.Data;

@Data
public class SubmitSolutionDto {
    private Language language;
    private String solution;
}