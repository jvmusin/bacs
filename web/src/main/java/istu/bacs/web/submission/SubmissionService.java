package istu.bacs.web.submission;

import istu.bacs.db.contest.Contest;
import istu.bacs.db.contest.ContestProblem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.model.submission.SubmitSolution;

import java.util.List;

/**
 * Интерфейс, описывающий работу с посылками.
 */
public interface SubmissionService {

    /**
     * Ищет посылку с {@link Submission#submissionId} равным {@code submissionId}.
     *
     * @param submissionId id посылки для поиска.
     * @return посылку с {@link Submission#submissionId} равным {@code submissionId},
     * либо {@literal null}, если посылка не найдена.
     */
    Submission findById(int submissionId);

    /**
     * Ищет посылки по {@link Contest#contestId}, {@link ContestProblem#problemIndex} и {@link User#username}.
     * <p>
     * Если {@code contestId} не равен {@literal null},
     * то производится поиск посылок по контесту с {@link Contest#contestId} равным {@code contestId}.
     * <p>
     * Если при этом указан ещё и {@code problemIndex},
     * то производится поиск посылок по задаче с индексом {@link ContestProblem#problemIndex}
     * равным {@code problemIndex}.
     * <p>
     * Если указан {@code authorUsername}, то производится поиск посылок,
     * автор которых имеет {@link User#username} равный {@code username}.
     *
     * @param contestId      {@link Contest#contestId} посылок для поиска. Если равен {@code null}, то игнорируется.
     * @param problemIndex   {@link ContestProblem#problemIndex} посылок для поиска. Если равен {@code null}, то игнорируется.
     * @param authorUsername {@link User#username} автора посылок для поиска. Если равен {@code null}, то игнорируется.
     * @return все посылки, удовлетворяющие требованиям из параметров.
     */
    List<Submission> findAll(Integer contestId,
                             String problemIndex,
                             String authorUsername);

    /**
     * Отправляет решение на проверку.
     *
     * @param sol решение для отправки на проверку.
     * @return {@link Submission#submissionId} отправленного решения.
     */
    int submit(SubmitSolution sol);
}