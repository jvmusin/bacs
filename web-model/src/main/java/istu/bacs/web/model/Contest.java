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
        return contest.map(c -> new Contest(
                c.getContestId(),
                c.getName(),
                formatDateTime(c.getStartTime()),
                formatDateTime(c.getFinishTime()),
                convertProblems(c.getProblems())
        ));
    }

    private static Problem[] convertProblems(List<ContestProblem> problems) {
        return Flux.fromIterable(problems)
                .transform(Problem::fromDbContestProblems)
                .collectList()
                .map(l -> l.toArray(new Problem[0]))
                .block();
    }
}