package istu.bacs.web.model.get;

import lombok.Value;

@Value
public class Standings {
    Contest contest;
    ContestantRow[] rows;
}