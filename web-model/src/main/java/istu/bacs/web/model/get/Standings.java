package istu.bacs.web.model.get;

import lombok.Value;

import java.util.List;

@Value
public class Standings {
    Contest contest;
    List<ContestantRow> rows;
}