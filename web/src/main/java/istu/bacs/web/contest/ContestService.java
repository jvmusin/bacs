package istu.bacs.web.contest;

import istu.bacs.db.contest.Contest;
import istu.bacs.web.model.contest.builder.EditContest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContestService {
    Contest findById(int contestId);
    List<Contest> findAll(Pageable pageable);
    int createContest(EditContest contest);
    void editContest(EditContest contest, int contestId);
}