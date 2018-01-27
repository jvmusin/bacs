package istu.bacs.externalapi.aggregator;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import istu.bacs.externalapi.ExternalApi;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

class ExternalApiAggregator implements ExternalApi {

    private final ExternalApi[] externalApis;

    ExternalApiAggregator(ExternalApi[] externalApis) {
        this.externalApis = externalApis;
    }

    @Override
    public List<Problem> getAllProblems() {
        return Arrays.stream(externalApis).parallel()
                .map(ExternalApi::getAllProblems)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Override
    public boolean submit(Submission submission) {
        return submit(singletonList(submission));
    }

    @Override
    public boolean submit(List<Submission> submissions) {
        Map<String, List<Submission>> byResource = splitByResource(submissions);
        //noinspection ReplaceInefficientStreamCount
        return byResource.entrySet().parallelStream().filter(resourceAndSubmissions -> {
            String resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            return findApi(resource).submit(resourceSubmissions);
        }).count() > 0;
    }

    @Override
    public void checkSubmissionResult(Submission submission) {
        checkSubmissionResult(singletonList(submission));
    }

    @Override
    public boolean checkSubmissionResult(List<Submission> submissions) {
        Map<String, List<Submission>> byResource = splitByResource(submissions);
        //noinspection ReplaceInefficientStreamCount
        return byResource.entrySet().parallelStream().filter(resourceAndSubmissions -> {
            String resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            return findApi(resource).checkSubmissionResult(resourceSubmissions);
        }).count() > 0;
    }

    private Map<String, List<Submission>> splitByResource(List<Submission> submissions) {
        return submissions.stream().collect(groupingBy(
                Submission::getResourceName,
                toList()
        ));
    }

    private ExternalApi findApi(String resourceName) {
        for (ExternalApi api : externalApis)
            if (api.getApiName().equals(resourceName))
                return api;
        throw new RuntimeException("No such api: " + resourceName);
    }

    @Override
    public String getApiName() {
        throw new UnsupportedOperationException();
    }
}