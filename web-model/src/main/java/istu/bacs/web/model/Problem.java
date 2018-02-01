package istu.bacs.web.model;

import istu.bacs.db.contest.ContestProblem;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Value
public class Problem {
    String index;
    String name;
    Integer contestId;
    int timeLimitMillis;
    int memoryLimitBytes;
    String statementUrl;

    public static Flux<Problem> fromDbContestProblems(Flux<ContestProblem> contestProblems) {
        return contestProblems.map(Problem::convert);
    }

    public static Mono<Problem> fromDbContestProblem(Mono<ContestProblem> contestProblem) {
        return contestProblem.map(Problem::convert);
    }

    public static Flux<Problem> fromDbProblems(Flux<istu.bacs.db.problem.Problem> problems) {
        return problems.map(Problem::convert);
    }

    public static Mono<Problem> fromDbProblem(Mono<istu.bacs.db.problem.Problem> problem) {
        return problem.map(Problem::convert);
    }

    private static Problem convert(ContestProblem cp) {
        return new Problem(
                cp.getProblemIndex(),
                cp.getProblem().getName(),
                cp.getContest().getContestId(),
                cp.getProblem().getTimeLimitMillis(),
                cp.getProblem().getMemoryLimitBytes(),
                cp.getProblem().getStatementUrl()
        );
    }

    private static Problem convert(istu.bacs.db.problem.Problem p) {
        return new Problem(
                p.getProblemId(),
                p.getName(),
                null,
                p.getTimeLimitMillis(),
                p.getMemoryLimitBytes(),
                p.getStatementUrl()
        );
    }
}