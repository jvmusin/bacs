package istu.bacs.externalapi.aggregator;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.problem.ResourceName;
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
        Map<ResourceName, List<Submission>> byResource = splitByResource(submissions);
        //noinspection ReplaceInefficientStreamCount
        return byResource.entrySet().parallelStream().filter(resourceAndSubmissions -> {
            ResourceName resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            return findApi(resource).submit(resourceSubmissions);
        }).count() > 0;
    }

    @Override
    public boolean checkSubmissionResult(Submission submission) {
        return checkSubmissionResult(singletonList(submission));
    }

    @Override
    public boolean checkSubmissionResult(List<Submission> submissions) {
        Map<ResourceName, List<Submission>> byResource = splitByResource(submissions);
        //noinspection ReplaceInefficientStreamCount
        return byResource.entrySet().parallelStream().filter(resourceAndSubmissions -> {
            ResourceName resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            return findApi(resource).checkSubmissionResult(resourceSubmissions);
        }).count() > 0;
    }

    private Map<ResourceName, List<Submission>> splitByResource(List<Submission> submissions) {
        return submissions.stream().collect(groupingBy(
                s -> s.getProblem().getResourceName(),
                toList()
        ));
    }

    private ExternalApi findApi(ResourceName resourceName) {
        for (ExternalApi api : externalApis)
            if (api.getResourceName().equals(resourceName))
                return api;
        throw new ApiNotFoundException("No such api: " + resourceName);
    }

    @Override
    public ResourceName getResourceName() {
        throw new UnsupportedOperationException();
    }
}