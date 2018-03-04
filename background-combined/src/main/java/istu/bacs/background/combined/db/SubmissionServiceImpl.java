package istu.bacs.background.combined.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionRepository;
import istu.bacs.db.submission.Verdict;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public List<Submission> findAllByVerdict(Verdict verdict) {
        List<Submission> submissions = submissionRepository.findAllByResultVerdict(verdict);
        initializeSubmissions(submissions);
        return submissions;
    }

    @Override
    @Transactional
    public List<Submission> findAllByIds(Iterable<Integer> ids) {
        List<Submission> submissions = submissionRepository.findAllById(ids);
        initializeSubmissions(submissions);
        return submissions;
    }

    @Override
    public void save(Submission submission) {
        submissionRepository.save(submission);
    }

    /**
     * Инициализирует все лениво-загружаемые поля посылки.
     *
     * @param submission посылка, поля которой необходимо инициализировать.
     */
    private void initializeSubmission(Submission submission) {
        if (submission != null) {
            //noinspection ResultOfMethodCallIgnored
            submission.getContest();
        }
    }

    /**
     * Инициализирует все лениво-загружаемые поля посылок.
     *
     * @param submissions посылки, поля которых необходимо инициализировать.
     */
    private void initializeSubmissions(Iterable<Submission> submissions) {
        submissions.forEach(this::initializeSubmission);
    }
}