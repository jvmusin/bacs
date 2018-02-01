package istu.bacs.web.model;

import lombok.Value;

import java.util.List;

@Value
public class Standings {
    Contest contest;
    List<ContestantRow> rows;
}