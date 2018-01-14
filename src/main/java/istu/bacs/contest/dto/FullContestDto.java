package istu.bacs.contest.dto;

import istu.bacs.contest.Contest;
import istu.bacs.problem.dto.ProblemDto;
import lombok.Data;

import java.util.List;

@Data
public class FullContestDto {

    private ContestMetaDto meta;
    private List<ProblemDto> problems;

    public FullContestDto(Contest contest) {
        meta = new ContestMetaDto(contest);
        problems = ProblemDto.forContest(contest);
    }
}