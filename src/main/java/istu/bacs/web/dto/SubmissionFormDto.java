package istu.bacs.web.dto;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SubmissionFormDto {
    private int contestId;
    private Set<Language> languages;
    private List<ProblemDto> problems;

    public SubmissionFormDto(int contestId, Set<Language> languages, List<Problem> problems) {
        this.contestId = contestId;
        this.languages = languages;
        this.problems = ProblemDto.convert(problems, contestId);
    }

    public String getSubmitSolutionUrl() {
        return "/contest/" + contestId + "/submit";
    }
}