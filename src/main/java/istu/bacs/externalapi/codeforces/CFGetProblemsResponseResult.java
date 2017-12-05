package istu.bacs.externalapi.codeforces;

import lombok.Data;

import java.util.List;

@Data
public class CFGetProblemsResponseResult {
    private List<CFProblem> problems;
    private List<CFProblemStatistics> problemStatistics;
}