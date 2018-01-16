package istu.bacs.web.externalapi;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static istu.bacs.web.externalapi.ExternalApiHelper.extractResource;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class ExternalApiAggregatorImpl implements ExternalApiAggregator {

    private final ExternalApi[] externalApis;

    public ExternalApiAggregatorImpl(ExternalApi[] externalApis) {
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
    public void submit(Submission submission) {
        String problemId = submission.getProblem().getProblemId();
        findApi(extractResource(problemId)).submit(submission);
    }

    @Override
    public void submit(List<Submission> submissions) {
        Map<String, List<Submission>> byResource = submissions.stream()
                .collect(groupingBy(s -> extractResource(s.getProblem().getProblemId()), toList()));
        byResource.entrySet().parallelStream().forEach(resourceAndSubmissions -> {
            String resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            findApi(resource).submit(resourceSubmissions);
        });
    }

    @Override
    public void updateSubmissionDetails(List<Submission> submissions) {
        Map<String, List<Submission>> byResource = submissions.stream()
                .collect(groupingBy(s -> extractResource(s.getExternalSubmissionId()), toList()));
        byResource.entrySet().parallelStream().forEach(resourceAndSubmissions -> {
            String resource = resourceAndSubmissions.getKey();
            List<Submission> resourceSubmissions = resourceAndSubmissions.getValue();
            findApi(resource).updateSubmissionDetails(resourceSubmissions);
        });
    }

    private ExternalApi findApi(String resourceName) {
        for (ExternalApi api : externalApis)
            if (api.getResourceName().equals(resourceName))
                return api;
        throw new RuntimeException("No such api: " + resourceName);
    }
}