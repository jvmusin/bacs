package istu.bacs.web.submission.dto;

import istu.bacs.db.submission.Submission;
import lombok.Data;

@Data
public class FullSubmissionDto {

    private SubmissionMetaDto meta;
    private String solution;

    public FullSubmissionDto(Submission submission) {
        meta = new SubmissionMetaDto(submission);
        solution = submission.getSolution();
    }
}