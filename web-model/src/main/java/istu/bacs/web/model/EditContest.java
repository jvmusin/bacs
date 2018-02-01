package istu.bacs.web.model;

import lombok.Value;

@Value
public class EditContest {
    String name;
    String startTime;
    String finishTime;
    EditContestProblem[] problems;
}