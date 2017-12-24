package istu.bacs.contest.dto;

import istu.bacs.contest.Contest;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class ContestMetaDto {

    private int id;
    private String name;
    private String startTime;
    private String finishTime;

    public ContestMetaDto(Contest contest) {
        id = contest.getContestId();
        name = contest.getContestName();
        startTime = contest.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME);
        finishTime = contest.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
