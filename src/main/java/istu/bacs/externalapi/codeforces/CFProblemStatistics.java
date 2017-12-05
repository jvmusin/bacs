package istu.bacs.externalapi.codeforces;

import lombok.Data;

@Data
public class CFProblemStatistics {
    private int contestId;
    private String index;
    private int solvedCount;
}