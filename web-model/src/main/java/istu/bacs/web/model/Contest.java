package istu.bacs.web.model;

import istu.bacs.db.contest.ContestProblem;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Contest {
    int id;
    String name;
    String startTime;
    String finishTime;
    Problem[] problems;

    public static Mono<Contest> fromDb(Mono<istu.bacs.db.contest.Contest> contest) {
        return contest.map(Contest::convert);
    }

    public static Flux<Contest> fromDb(Flux<istu.bacs.db.contest.Contest> contests) {
        return contests.map(Contest::convert);
    }

    private static Contest convert(istu.bacs.db.contest.Contest contest) {
        return new Contest(
                contest.getContestId(),
                contest.getName(),
                formatDateTime(contest.getStartTime()),
                formatDateTime(contest.getFinishTime()),
                convertProblems(contest.getProblems())
        );
    }

    private static Problem[] convertProblems(List<ContestProblem> problems) {
        return Flux.fromIterable(problems)
                .transform(Problem::fromDbContestProblems)
                .collectList()
                .map(l -> l.toArray(new Problem[0]))
                .block();
    }
}