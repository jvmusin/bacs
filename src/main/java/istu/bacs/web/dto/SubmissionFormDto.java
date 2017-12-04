package istu.bacs.web.dto;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class SubmissionFormDto {
    private Set<Language> languages;
    private List<ProblemDto> problems;
    private String submitSolutionUrl;

    public SubmissionFormDto(int contestId, Set<Language> languages, List<Problem> problems) {
        this.languages = languages;

        this.problems = new ArrayList<>(problems.size());
        for (int i = 0; i < problems.size(); i++)
            this.problems.add(new ProblemDto(contestId, i, problems.get(i)));
        submitSolutionUrl = "/contest/" + contestId + "/submit";
    }
}