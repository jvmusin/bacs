package istu.bacs.web;

import istu.bacs.service.ContestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	
	private final ContestService contestService;
	
	public ContestController(ContestService contestService) {
		this.contestService = contestService;
	}
	
	@RequestMapping("/contests")
	public String getAllContests(Model model) {
		model.addAttribute("contests", contestService.findAll());
		return VIEWS_CONTEST_LIST;
	}
	
}