package istu.bacs.web.model.post;

import lombok.Value;

@Value
public class EditContest {
    String id;
    String name;
    String startTime;
    String finishTime;
    int[] problemIds;
}