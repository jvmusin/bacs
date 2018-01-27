package istu.bacs.web.model;

import lombok.Value;

@Value
public class Standings {
    Contest contest;
    ContestantRow[] rows;
}