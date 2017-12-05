package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static istu.bacs.externalapi.ExternalApiHelper.extractResource;
import static java.util.stream.Collectors.toList;

@Service
class ExternalApiAggregatorImpl implements ExternalApiAggregator {

    private final ExternalApi[] externalApis;

    public ExternalApiAggregatorImpl(ExternalApi[] externalApis) {
        this.externalApis = externalApis;
    }

    @Override
    public Problem getProblem(String problemId) {
        return findApi(extractResource(problemId)).getProblem(problemId);
    }

    @Override
    public URI getStatementUrl(String problemId) {
        return findApi(extractResource(problemId)).getStatementUrl(problemId);
    }

    @Override
    public void submit(boolean pretestsOnly, Submission submission) {
        String problemId = submission.getProblem().getProblemId();
        findApi(extractResource(problemId)).submit(pretestsOnly, submission);
    }

    @Override
    public void submit(boolean pretestsOnly, List<Submission> submissions) {
        if (submissions.isEmpty()) return;
        String submissionId = submissions.get(0).getExternalSubmissionId();
        findApi(extractResource(submissionId)).submit(pretestsOnly, submissions);
    }

    @Override
    public void updateSubmissions(List<Submission> submissions) {
        List<Problem> problems = submissions.stream().map(Submission::getProblem).distinct().collect(toList());
        updateProblems(problems);

        Map<String, List<Submission>> byResource = submissions.stream()
                .collect(Collectors.groupingBy(s -> extractResource(s.getExternalSubmissionId()), toList()));
        byResource.forEach((resource, subs) -> findApi(resource).updateSubmissions(subs));
    }

    @Override
    public void updateProblems(List<Problem> problems) {
        Map<String, List<Problem>> byResource = problems.stream()
                .collect(Collectors.groupingBy(s -> extractResource(s.getProblemId()), toList()));
        byResource.forEach((resource, prob) -> findApi(resource).updateProblemDetails(prob));
    }

    @Override
    public Set<Language> getSupportedLanguages(String resourceName) {
        return findApi(resourceName).getSupportedLanguages();
    }

    private ExternalApi findApi(String resourceName) {
        for (ExternalApi api : externalApis)
            if (api.getResourceName().equals(resourceName))
                return api;
        throw new RuntimeException("No such api: " + resourceName);
    }
}