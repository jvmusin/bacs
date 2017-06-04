package istu.bacs;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.model.User;
import istu.bacs.model.type.Language;
import istu.bacs.model.type.Verdict;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.service.SubmissionService;
import istu.bacs.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
	
	private final ContestService contestService;
	private final ProblemService problemService;
	private final SubmissionService submissionService;
	private final UserService userService;
	
	private static final User tourist = new User(1, "tourist");
	private static final User petr = new User(2, "Petr");
	private static final User musin = new User(3, "Musin");
	
	public DataLoader(ContestService contestService, ProblemService problemService, SubmissionService submissionService, UserService userService) {
		this.contestService = contestService;
		this.problemService = problemService;
		this.submissionService = submissionService;
		this.userService = userService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initUsers();
		initProblems();
		initContests();
		bindProblemsToContests();
		initFirstContestSubmissions();
		initSecondContestSubmissions();
	}
	
	private void initUsers() {
		userService.save(tourist);
		userService.save(petr);
		userService.save(musin);
	}
	
	private void initProblems() {
		problemService.save(new Problem(1, "A + B",   1.5 * 1000, 64.0  * 1024 * 1024));
		problemService.save(new Problem(3, "Maze",    2.0 * 1000, 256.0 * 1024 * 1024));
		problemService.save(new Problem(2, "Theatre", 2.0 * 1000, 128.0 * 1024 * 1024));
		problemService.save(new Problem(4, "Bit++",   9.9 * 1000, 64.0  * 1024 * 1024));
		problemService.save(new Problem(5, "Team",    2.0 * 1000, 128.0 * 1024 * 1024));
		problemService.save(new Problem(6, "Taxi",    2.0 * 1000, 256.0 * 1024 * 1024));
	}
	
	private void initContests() {
		LocalDateTime firstContestStart = LocalDateTime.of(2017, 1, 1, 0, 1);
		LocalDateTime firstContestFinish = firstContestStart.plusHours(5);
		contestService.save(new Contest(1, "Happy New Year", firstContestStart, firstContestFinish, new ArrayList<>()));
		
		LocalDateTime secondContestStart = LocalDateTime.of(2017, 4, 23, 12, 0);
		LocalDateTime secondContestFinish = secondContestStart.plusHours(3);
		contestService.save(new Contest(2, "Kotlin Love Day", secondContestStart, secondContestFinish, new ArrayList<>()));
	}
	
	private void bindProblemsToContests() {
		for (int i = 1; i <= 2; i++) contestService.findById(1).getProblems().add(problemService.findById(i));
		for (int i = 3; i <= 6; i++) contestService.findById(2).getProblems().add(problemService.findById(i));
	}
	
	private void initFirstContestSubmissions() {
		Contest contest = contestService.findById(1);
		List<Problem> problems = contest.getProblems();
		
		submissionService.save(new Submission(1, tourist, contest,
				problems.get(0), contest.getStartTime().plusMinutes(5), Language.CPP, "...C++ code...",
				Verdict.Accepted, null, 0.25 * 1000, 5.0 * 1024 * 1024));
		
		submissionService.save(new Submission(2, musin, contest,
				problems.get(0), contest.getStartTime().plusMinutes(6).plusSeconds(21), Language.Kotlin, "...Kotlin code...",
				Verdict.Accepted, null, 1.2 * 1000, 21.0 * 1024 * 1024));
		
		submissionService.save(new Submission(3, petr, contest,
				problems.get(0), contest.getStartTime().plusMinutes(7), Language.Kotlin, "...Java code...",
				Verdict.CompileError, null, null, null));
		
		submissionService.save(new Submission(4, petr, contest,
				problems.get(0), contest.getStartTime().plusMinutes(7).plusSeconds(13), Language.Java, "...Java code...",
				Verdict.Accepted, null, 1.35 * 1000, 23.3 * 1024 * 1024));
		
		submissionService.save(new Submission(5, musin, contest,
				problems.get(1), contest.getStartTime().plusHours(2).plusMinutes(41), Language.Java, "...Slow Java code...",
				Verdict.TimeLimitExceeded, 4, 2.0 * 1000, 201.0 * 1024 * 1024));
		
		submissionService.save(new Submission(6, musin, contest,
				problems.get(1), contest.getStartTime().plusHours(2).plusMinutes(56), Language.Java, "...Slow Java code...",
				Verdict.TimeLimitExceeded, 6, 2.0 * 1000, 72.0 * 1024 * 1024));
		
		submissionService.save(new Submission(7, petr, contest,
				problems.get(1), contest.getStartTime().plusHours(4).plusMinutes(20), Language.Java, "...Fast Java code...",
				Verdict.Accepted, null, 0.89 * 1000, 102.0 * 1024 * 1024));
		
		submissionService.save(new Submission(8, tourist, contest,
				problems.get(1), contest.getStartTime().plusHours(4).plusMinutes(23).plusSeconds(11), Language.CPP, "...Fast C++ code...",
				Verdict.Accepted, null, 0.5 * 1000, 55.0 * 1024 * 1024));
		
		submissionService.save(new Submission(9, musin, contest,
				problems.get(1), contest.getStartTime().plusHours(4).plusMinutes(50).plusSeconds(45), Language.Python, "...Fast Python code...",
				Verdict.Accepted, null, 0.97 * 1000, 155.0 * 1024 * 1024));
	}
	
	private void initSecondContestSubmissions() {
		Contest contest = contestService.findById(2);
		List<Problem> problems = contest.getProblems();
		
		submissionService.save(new Submission(10, musin, contest,
				problems.get(0), contest.getStartTime().plusMinutes(50).plusSeconds(45), Language.Kotlin, "...Kotlin code...",
				Verdict.Accepted, null, 1.3 * 1000, 30.0 * 1024 * 1024));
		
		submissionService.save(new Submission(11, musin, contest,
				problems.get(1), contest.getStartTime().plusHours(1).plusMinutes(20).plusSeconds(1), Language.Kotlin, "...Kotlin code...",
				Verdict.Accepted, null, 8.7 * 1000, 56.0 * 1024 * 1024));
		
		submissionService.save(new Submission(12, musin, contest,
				problems.get(2), contest.getStartTime().plusHours(2).plusSeconds(30), Language.Kotlin, "...Kotlin code...",
				Verdict.Accepted, null, 0.3 * 1000, 120.0 * 1024 * 1024));
		
		submissionService.save(new Submission(13, musin, contest,
				problems.get(3), contest.getStartTime().plusHours(2).plusMinutes(31), Language.Kotlin, "...Unoptimized Kotlin code...",
				Verdict.MemoryLimitExceeded, 112, 0.19 * 1000, 256.0 * 1024 * 1024));
		
		submissionService.save(new Submission(14, musin, contest,
				problems.get(3), contest.getStartTime().plusHours(2).plusMinutes(36), Language.Kotlin, "...Optimized Kotlin code...",
				Verdict.Accepted, null, 0.21 * 1000, 241.1 * 1024 * 1024));
	}
}