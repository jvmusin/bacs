package istu.bacs;

import istu.bacs.standings.StandingsService;
import istu.bacs.submission.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static istu.bacs.submission.Verdict.NOT_SUBMITTED;
import static istu.bacs.submission.Verdict.PENDING;
import static java.util.stream.Collectors.toList;

@Component
public class PlatformInitializer {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;

    private final SubmissionSubmitter submissionSubmitter;
    private final SubmissionRefresher submissionRefresher;

    private final StandingsService standingsService;

    public PlatformInitializer(SubmissionRepository submissionRepository, SubmissionResultRepository submissionResultRepository, SubmissionSubmitter submissionSubmitter, SubmissionRefresher submissionRefresher, StandingsService standingsService) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
        this.submissionSubmitter = submissionSubmitter;
        this.submissionRefresher = submissionRefresher;
        this.standingsService = standingsService;
    }

    @PostConstruct
    public void initialize() {
        initSubmissionSubmitter();
        initSubmissionRefresher();
        initStandings();
    }

    private void initSubmissionSubmitter() {
        submissionSubmitter.addAll(findByVerdict(NOT_SUBMITTED));
    }

    private void initSubmissionRefresher() {
        submissionRefresher.addAll(findByVerdict(PENDING));
    }

    private void initStandings() {
        //todo: works slow cause of checking all submissions
        List<Submission> submissions = submissionRepository.findAll().stream()
                .filter(sub -> sub.getVerdict() != NOT_SUBMITTED && sub.getVerdict() != PENDING)
                .collect(toList());
        standingsService.update(submissions);
    }

    private List<Submission> findByVerdict(Verdict verdict) {
        return submissionResultRepository.findByVerdict(verdict).stream()
                .map(SubmissionResult::getSubmission)
                .collect(toList());
    }
}