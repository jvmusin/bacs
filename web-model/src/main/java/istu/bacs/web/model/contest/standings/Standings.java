package istu.bacs.web.model.contest.standings;

import istu.bacs.web.model.contest.Contest;
import lombok.Value;

import java.util.List;

@Value
public class Standings {
    Contest contest;
    List<ContestantRow> rows;
}