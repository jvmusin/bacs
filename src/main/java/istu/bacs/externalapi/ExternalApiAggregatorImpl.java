package istu.bacs.externalapi;

import istu.bacs.model.Contest;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class ExternalApiAggregatorImpl implements ExternalApiAggregator {

    private final ExternalApi[] externalApis;

    public ExternalApiAggregatorImpl(ExternalApi[] externalApis) {
        this.externalApis = externalApis;
    }

    @Override
    public Problem getProblem(String problemId) {
        return findApi(extractResourceName(problemId)).getProblem(problemId);
    }

    @Override
    public URI getStatementUrl(String problemId) {
        return findApi(extractResourceName(problemId)).getStatementUrl(problemId);
    }

    @Override
    public void submit(boolean pretestsOnly, Submission submission) {
        String problemId = submission.getProblem().getProblemId();
        findApi(extractResourceName(problemId)).submit(pretestsOnly, submission);
    }

    @Override
    public void submit(boolean pretestsOnly, Submission... submissions) {
        String problemId = submissions[0].getProblem().getProblemId();
        findApi(extractResourceName(problemId)).submit(pretestsOnly, submissions);
    }

    @Override
    public void updateSubmissionResults(List<Submission> submissions) {
        if (submissions.isEmpty()) return;
        String problemId = submissions.get(0).getProblem().getProblemId();
        //todo: may not work if different clients (sybon and smth else), solve it
        findApi(extractResourceName(problemId)).updateSubmissionResults(submissions);
    }

    @Override
    public void updateProblemDetails(List<Problem> problems) {
        if (problems.isEmpty()) return;
        String problemId = problems.get(0).getProblemId();
        findApi(extractResourceName(problemId)).updateProblemDetails(problems);
    }

    @Override
    public void updateContest(Contest contest) {
        updateProblemDetails(contest.getProblems());
        updateSubmissionResults(contest.getSubmissions());
    }

    private String extractResourceName(String s) {
        return s.split("@", 2)[0];
    }

    @Override
    public ExternalApi findApi(String resourceName) {
        for (ExternalApi api : externalApis)
            if (api.getResourceName().equals(resourceName))
                return api;
        throw new RuntimeException("No such api: " + resourceName);
    }
}