package istu.bacs.web.controller;

import istu.bacs.domain.Contest;
import istu.bacs.domain.Problem;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.web.dto.contestbuilder.ContestBuilderDto;
import istu.bacs.web.dto.contestbuilder.NewContestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class ContestBuilderController {

    private final ProblemService problemService;
    private final ContestService contestService;

    public ContestBuilderController(ProblemService problemService, ContestService contestService) {
        this.problemService = problemService;
        this.contestService = contestService;
    }

    @GetMapping("/contests/builder")
    public ModelAndView loadContestBuilder() {
        return new ModelAndView("contests/contest-builder", "model", new ContestBuilderDto(problemService.findAll()));
    }

    @PostMapping("/contests/build")
    public RedirectView buildContest(@ModelAttribute NewContestDto contest) {
        Contest cont = new Contest();

        cont.setContestName(contest.getName());
        cont.setStartTime(contest.getStartTime());
        cont.setFinishTime(contest.getFinishTime());

        List<Problem> problems = contest.getProblemIds().stream().map(problemService::findById).collect(toList());
        cont.setProblems(problems);

        contestService.save(cont);
        return new RedirectView("/contest/" + cont.getContestId());
    }
}