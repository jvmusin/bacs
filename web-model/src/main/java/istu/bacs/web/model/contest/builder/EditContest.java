package istu.bacs.web.model.contest.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditContest {
    private String name;
    private String startTime;
    private String finishTime;
    private List<EditContestProblem> problems;
}