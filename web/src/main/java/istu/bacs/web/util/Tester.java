package istu.bacs.web.util;

import istu.bacs.db.contest.Contest;
import istu.bacs.web.contest.ContestService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class Tester implements PlatformUnitInitializer {

    private final ContestService contestService;

    public Tester(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    @Transactional
    public void init() {
        List<Contest> contests = contestService.findAll(Pageable.unpaged());
        for (Contest contest : contests) {
            System.out.println(contest);
        }
    }
}
