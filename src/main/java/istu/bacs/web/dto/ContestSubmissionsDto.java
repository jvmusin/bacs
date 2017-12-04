package istu.bacs.web.dto;

import istu.bacs.model.Submission;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class ContestSubmissionsDto {
    private String contestName;
    private List<SubmissionDto> submissions;

    public ContestSubmissionsDto(String contestName, List<Submission> submissions) {
        this.contestName = contestName;
        this.submissions = submissions.stream().map(SubmissionDto::new).collect(toList());
    }
}