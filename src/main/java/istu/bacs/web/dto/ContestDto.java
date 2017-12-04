package istu.bacs.web.dto;

import istu.bacs.model.Contest;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ContestDto {

    private static final String TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private int contestId;
    private String contestName;
    private String startTime;
    private String finishTime;

    private List<ProblemDto> problems;

    private ContestDto(Contest contest) {
        contestId = contest.getContestId();
        contestName = contest.getContestName();
        startTime = formatter.format(contest.getStartTime());
        finishTime = formatter.format(contest.getFinishTime());
    }

    public String getContestUrl() {
        return "/contest/" + contestId;
    }

    public static ContestDto withoutProblems(Contest contest) {
        return new ContestDto(contest);
    }

    public static ContestDto withProblems(Contest contest) {
        ContestDto dto = new ContestDto(contest);
        dto.problems = ProblemDto.convert(contest.getProblems(), contest.getContestId());
        return dto;
    }
}