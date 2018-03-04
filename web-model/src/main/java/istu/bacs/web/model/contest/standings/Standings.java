package istu.bacs.web.model.contest.standings;

import istu.bacs.web.model.contest.Contest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Standings {
    private Contest contest;
    private List<ContestantRow> rows;
}