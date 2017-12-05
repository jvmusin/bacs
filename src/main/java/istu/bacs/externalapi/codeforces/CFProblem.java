package istu.bacs.externalapi.codeforces;

import lombok.Data;

@Data
public class CFProblem {
    private int contestId;
    private String index;
    private String name;
    private CFProblemType type;
    private Double points;
    private String[] tags;
}