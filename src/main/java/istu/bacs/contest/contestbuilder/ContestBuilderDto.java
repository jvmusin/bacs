package istu.bacs.contest.contestbuilder;

import istu.bacs.problem.Problem;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class ContestBuilderDto {
    private List<ContestBuilderProblemDto> problems;

    public ContestBuilderDto(List<Problem> problems) {
        this.problems = problems.stream()
                .sorted()
                .map(ContestBuilderProblemDto::new)
                .collect(toList());
    }

    public String getBuildContestUrl() {
        return "/contests/build";
    }
}