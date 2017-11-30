package istu.bacs.service.impl;

import istu.bacs.model.Contest;
import istu.bacs.model.Submission;
import istu.bacs.repository.SubmissionRepository;
import istu.bacs.service.ContestService;
import istu.bacs.service.ProblemService;
import istu.bacs.service.SubmissionService;
import istu.bacs.sybon.SybonApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private final SubmissionRepository submissionRepository;
	private final ProblemService problemService;
	private final ContestService contestService;
	private final SybonApi sybon;
	
	public SubmissionServiceImpl(SubmissionRepository submissionRepository, ProblemService problemService, ContestService contestService, SybonApi sybon) {
		this.submissionRepository = submissionRepository;
        this.problemService = problemService;
        this.contestService = contestService;
        this.sybon = sybon;
    }
	
	@Override
	public Submission findById(Integer id) {
		return submissionRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Submission> findAllByContestId(Integer contestId) {
		Contest contest = contestService.findById(contestId);
		List<Submission> submissions = contest.getSubmissions();
        int[] submissionIds = submissions.stream().mapToInt(Submission::getSubmissionId).toArray();
        Submission.SubmissionResult[] submissionResults = sybon.getSubmissionResults(submissionIds);
        for (int i = 0; i < submissions.size(); i++) {
            Submission cur = submissions.get(i);
            cur.setResult(submissionResults[i]);
            cur.setProblem(problemService.findById(cur.getProblem().getProblemId()));
        }
        return submissions;
	}

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
        sybon.submit(submission, pretestsOnly);
        submissionRepository.save(submission);
    }
}