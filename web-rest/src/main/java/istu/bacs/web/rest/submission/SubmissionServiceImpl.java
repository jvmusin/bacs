package istu.bacs.web.rest.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.user.User;
import istu.bacs.web.rest.contest.ContestService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final ContestService contestService;
    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(ContestService contestService, SubmissionRepository submissionRepository) {
        this.contestService = contestService;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public Mono<Submission> findById(Mono<Integer> submissionId) {
        return submissionId
                .map(submissionRepository::findById)
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    public Flux<Submission> findAll() {
        return Flux.fromIterable(submissionRepository.findAll());
    }

    @Override
    public Flux<Submission> findAllByContest(Mono<Integer> contestId) {
        return contestId
                .transform(contestService::findById)
                .map(Contest::getProblems)
                .flatMapMany(Flux::fromIterable)
                .map(submissionRepository::findAllByContestProblem)
                .flatMap(Flux::fromIterable);
    }

    @Override
    public Flux<Submission> findAllByContestAndProblemIndex(Mono<Integer> contestId, Mono<String> problemIndex) {
        return contestId.zipWith(problemIndex)
                .map(t -> ContestProblem.createId(t.getT1(), t.getT2()))
                .map(id -> ContestProblem.builder().contestProblemId(id).build())
                .map(submissionRepository::findAllByContestProblem)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<Submission> findAllByContestProblemAndChecked(String contestProblemId) {
        return Mono.just(contestProblemId)
                .map(id -> ContestProblem.builder().contestProblemId(id).build())
                .map(submissionRepository::findAllByContestProblemAndChecked)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<Submission> findAllByAuthor(Mono<User> author) {
        return author.flatMapIterable(submissionRepository::findAllByAuthor);
    }

    @Override
    public Mono<Submission> save(Mono<Submission> submission) {
        return submission.map(submissionRepository::save);
    }
}
