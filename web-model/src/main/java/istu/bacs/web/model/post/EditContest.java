package istu.bacs.web.model.post;

import lombok.Value;

import java.util.List;

@Value
public class EditContest {
    String name;
    String startTime;
    String finishTime;
    List<EditContestProblem> problems;
}