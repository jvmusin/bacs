package istu.bacs.web.contest.dto;

import istu.bacs.db.contest.Contest;
import istu.bacs.web.problem.dto.ProblemDto;
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