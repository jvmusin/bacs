package istu.bacs.web;

import istu.bacs.service.ContestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	
	private final ContestService contestService;
	
	public ContestController(ContestService contestService) {
		this.contestService = contestService;
	}
	
	@RequestMapping("/contests")
	public String getAllContests(Model model) {
		model.addAttribute("contests", contestService.findAll());
		return VIEWS_CONTEST_LIST;
	}
	
	@RequestMapping("/contest/{contestId}")
	public String getContest(Model model, @PathVariable Integer contestId) {
		model.addAttribute("contest", contestService.findById(contestId));
		return VIEWS_CONTEST_PROBLEMS;
	}
	
}