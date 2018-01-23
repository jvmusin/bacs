package istu.bacs.externalapi.fake;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import istu.bacs.externalapi.ExternalApi;

import java.util.List;
import java.util.Random;

import static istu.bacs.db.submission.Verdict.PENDING;

public class FakeApi implements ExternalApi {

    private final Random rnd = new Random();

    private final ProblemService problemService = new ProblemService();
    private final SubmissionResultUpdater submissionResultUpdater = new SubmissionResultUpdater();

    @Override
    public List<Problem> getAllProblems() {
        return problemService.getProblems();
    }

    @Override
    public void submit(Submission submission) {
        submission.setExternalSubmissionId(rnd.nextInt(Integer.MAX_VALUE));
        submission.getResult().setVerdict(PENDING);
    }

    @Override
    public void submit(List<Submission> submissions) {
        submissions.forEach(this::submit);
    }

    @Override
    public void checkSubmissionResult(Submission submission) {
        submissionResultUpdater.updateSubmissionResult(submission);
    }

    @Override
    public void checkSubmissionResult(List<Submission> submissions) {
        submissions.forEach(this::checkSubmissionResult);
    }

    @Override
    public String getApiName() {
        return "FAKE";
    }
}