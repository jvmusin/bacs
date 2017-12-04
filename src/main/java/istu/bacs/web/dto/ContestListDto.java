package istu.bacs.web.dto;

import istu.bacs.model.Contest;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class ContestListDto {
    private List<ContestDto> contests;

    public ContestListDto(List<Contest> contests) {
        this.contests = contests.stream()
                .map(ContestDto::withoutProblems)
                .collect(toList());
    }
}
