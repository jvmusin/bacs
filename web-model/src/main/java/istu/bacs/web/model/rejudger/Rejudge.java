package istu.bacs.web.model.rejudger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rejudge {
    int contestId;
    String problemIndex;
}