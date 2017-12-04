package istu.bacs.web.dto;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        List<Problem> contestProblems = contest.getProblems();
        dto.problems = new ArrayList<>(contestProblems.size());
        for (int i = 0; i < contestProblems.size(); i++)
            dto.problems.add(new ProblemDto(contest.getContestId(), i, contestProblems.get(i)));

        return dto;
    }
}