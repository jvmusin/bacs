package istu.bacs.web;

import istu.bacs.model.*;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.service.SubmissionService;
import istu.bacs.web.dto.*;
import istu.bacs.web.dto.contestbuilder.ContestBuilderDto;
import istu.bacs.web.dto.contestbuilder.NewContestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Controller
public class ContestController {
	
	private static final String VIEWS_CONTEST_LIST = "contests/contest-list";
	private static final String VIEWS_CONTEST_PROBLEMS = "contests/contest-problems";
	private static final String VIEWS_SUBMISSION_LIST = "contests/submissions/submission-list";
    private static final String VIEWS_SUBMISSION_VIEW = "contests/submissions/submission-view";
    private static final String VIEWS_SUBMIT_PAGE = "contests/submissions/submit-solution";

	private final ContestService contestService;
	private final SubmissionService submissionService;
	private final ProblemService problemService;

    public ContestController(ContestService contestService, SubmissionService submissionService, ProblemService problemService) {
		this.contestService = contestService;
		this.submissionService = submissionService;
        this.problemService = problemService;
    }
	
	@GetMapping("/contests")
	public ModelAndView loadAllContests() {
        List<Contest> contests = contestService.findAll();
        return new ModelAndView(VIEWS_CONTEST_LIST, "model", new ContestListDto(contests));
    }
	
	@GetMapping("/contest/{contestId}")
	public ModelAndView loadContestProblems(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);
        return new ModelAndView(VIEWS_CONTEST_PROBLEMS, "contest", ContestDto.withProblems(contest));
	}

    @GetMapping("/contest/{contestId}/problem/{problemLetter}")
    public RedirectView loadStatement(@PathVariable int contestId, @PathVariable char problemLetter) {
        Contest contest = contestService.findById(contestId);
        Problem problem = contest.getProblems().get(problemLetter - 'A');
        String statementUrl = problem.getDetails().getStatementUrl();
        return new RedirectView(statementUrl);
    }
	
	@GetMapping("/contest/{contestId}/submissions")
	public ModelAndView loadContestSubmissions(@PathVariable int contestId, @AuthenticationPrincipal User user) {
        Contest contest = contestService.findById(contestId);

        List<Submission> submissions = contest.getSubmissions().stream()
                .filter(s -> s.getAuthor().getUserId() == (int) user.getUserId())
                .collect(toList());
        submissionService.updateSubmissions(submissions);

        return new ModelAndView(VIEWS_SUBMISSION_LIST, "model",
                new ContestSubmissionsDto(contest.getContestName(), submissions));
	}
	
	@GetMapping("/contest/{contestId}/submit")
	public ModelAndView loadSubmissionForm(@PathVariable int contestId) {
        Contest contest = contestService.findById(contestId);

        return new ModelAndView(VIEWS_SUBMIT_PAGE, "model",
                new SubmissionFormDto(contestId, EnumSet.allOf(Language.class), contest.getProblems()));
    }
	
	@PostMapping("/contest/{contestId}/submit")
	public RedirectView submit(@ModelAttribute SubmissionDto submission,
                         @PathVariable int contestId,
                         @RequestParam MultipartFile file,
                         @AuthenticationPrincipal User user) throws IOException {
	    //todo: add user checking to controller
        if (!file.getOriginalFilename().isEmpty()) submission.setSolution(new String(file.getBytes()));

        Contest contest = contestService.findById(contestId);

        Submission sub = new Submission();
        sub.setAuthor(user);
        sub.setSolution(submission.getSolution());
        sub.setContest(contest);
        sub.setCreationTime(LocalDateTime.now());
        sub.setLanguage(submission.getLanguage());
        int problemIndex = submission.getProblem().getIndex();
        sub.setProblem(contest.getProblems().get(problemIndex));

        submissionService.submit(sub, false);
        return new RedirectView("/contest/{contestId}/submissions");
	}

    @GetMapping("/submission/{submissionId}")
    public ModelAndView loadSubmission(@AuthenticationPrincipal User user, @PathVariable int submissionId) {
        Submission submission = submissionService.findById(submissionId);
        //todo: add this to controller
        if (!submission.getAuthor().getUserId().equals(user.getUserId()))
            throw new SecurityException("Not enough rights to see this page");

        submissionService.updateSubmissions(singletonList(submission));
        return new ModelAndView(VIEWS_SUBMISSION_VIEW, "submission", new SubmissionDto(submission));
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
        return new RedirectView("/contests");
    }
}