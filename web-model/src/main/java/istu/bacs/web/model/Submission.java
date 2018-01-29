package istu.bacs.web.model;

import istu.bacs.db.submission.Language;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static istu.bacs.web.model.WebModelUtils.formatDateTime;

@Value
public class Submission {
    int id;
    User author;
    Problem problem;
    String created;
    Language language;
    String solution;
    SubmissionResult result;

    public static Mono<Submission> fromDb(Mono<istu.bacs.db.submission.Submission> submission) {
        return submission.map(Submission::convert);
    }

    public static Flux<Submission> fromDb(Flux<istu.bacs.db.submission.Submission> submission) {
        return submission.map(Submission::convert);
    }

    private static Submission convert(istu.bacs.db.submission.Submission s) {
        return new Submission(
                s.getSubmissionId(),
                User.fromDb(s.getAuthor()),
                Problem.fromDbContestProblem(Mono.just(s.getContestProblem())).block(),
                formatDateTime(s.getCreated()),
                s.getLanguage(),
                s.getSolution(),
                SubmissionResult.fromDb(s.getResult())
        );
    }
}