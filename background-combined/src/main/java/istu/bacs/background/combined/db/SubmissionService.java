package istu.bacs.background.combined.db;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.Verdict;

import java.util.List;

/**
 * Интерфейс, описывающий работу с посылками.
 */
public interface SubmissionService {

    /**
     * Возвращает все посылки с указанным вердиктом {@code verdict}.
     *
     * @param verdict вердикт, который должен быть в результате посылки.
     * @return список всех посылок с указанным вердиктом {@code verdict}.
     */
    List<Submission> findAllByVerdict(Verdict verdict);

    /**
     * Возвращает все посылки, у которых {@link Submission#submissionId} содержится в {@code ids}.
     *
     * @param ids множество {@code submissionId}, по которым производится поиск посылок.
     * @return список всех посылок, у которых {@link Submission#submissionId} содержится в {@code ids}.
     */
    List<Submission> findAllByIds(Iterable<Integer> ids);

    /**
     * Сохраняет посылку.
     *
     * @param submission посылка, которую необходимо сохранить.
     */
    void save(Submission submission);
}