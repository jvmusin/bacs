package istu.bacs.web.model;

import lombok.Value;

@Value
public class EditContest {
    Integer id;
    String name;
    String startTime;
    String finishTime;
    EditContestProblem[] problems;
}