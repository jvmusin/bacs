package istu.bacs.externalapi;

import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<Problem> getAllProblems() {
        return Arrays.stream(externalApis)
                .map(ExternalApi::getAllProblems)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Override
    public void submit(Submission submission) {
        String problemId = submission.getProblem().getProblemId();
        findApi(extractResource(problemId)).submit(submission);
    }

    @Override
    public void submitAll(List<Submission> submissions) {
        if (submissions.isEmpty()) return;
        String submissionId = submissions.get(0).getExternalSubmissionId();
        findApi(extractResource(submissionId)).submit(submissions);
    }

    @Override
    public void updateSubmissionDetails(List<Submission> submissions) {
        Map<String, List<Submission>> byResource = submissions.stream()
                .collect(Collectors.groupingBy(s -> extractResource(s.getExternalSubmissionId()), toList()));
        byResource.forEach((resource, subs) -> findApi(resource).updateSubmissionDetails(subs));
    }

    @Override
    public void updateProblemDetails(List<Problem> problems) {
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