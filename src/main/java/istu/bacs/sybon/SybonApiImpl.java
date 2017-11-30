package istu.bacs.sybon;

import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_MAP;
import static java.util.Collections.singletonMap;

@Service
class SybonApiImpl implements SybonApi {

    private final SybonConfigurationProperties config;
    private final SybonProblemConverter sybonProblemConverter;
    private final RestTemplate restTemplate;

    public SybonApiImpl(SybonConfigurationProperties config, SybonProblemConverter sybonProblemConverter, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.sybonProblemConverter = sybonProblemConverter;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Problem getProblem(int id) {
        URI uri = buildUrl(config.getProblemsUrl() + "/{id}", singletonMap("id", id));
        SybonProblem sybonProblem = restTemplate.getForObject(uri, SybonProblem.class);
        return sybonProblemConverter.convert(sybonProblem);
    }

    @Override
    public URI getStatementUrl(int problemId) {
        return buildUrl(config.getProblemsUrl() + "/{id}/statement", singletonMap("id", problemId));
    }

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
        SybonSubmit submit = new SybonSubmit();

        submit.setCompilerId(submission.getLanguage().getLanguageId());
        submit.setSolution(Base64.getEncoder().encodeToString(submission.getSolution().getBytes()));
        submit.setSolutionFileType("Text");
        submit.setProblemId(submission.getProblem().getProblemId());
        submit.setUserId(submission.getAuthor().getUserId());
        submit.setPretestsOnly(pretestsOnly);

        submission.setSubmissionId(submit(submit));
    }

    private int submit(SybonSubmit submit) {
        URI url = buildUrl(config.getSubmitsUrl() + "/send", EMPTY_MAP);
        return restTemplate.postForObject(url, submit, Integer.class);
    }

    @Override
    public SybonProblemCollection[] getProblemCollections(int offset, int limit) {
        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("Offset", offset);
        queryParams.put("Limit", limit);
        URI uri = buildUrl(config.getCollectionsUrl(), EMPTY_MAP, queryParams);
        return restTemplate.getForObject(uri, SybonProblemCollection[].class);
    }

    @Override
    public SybonProblemCollection getProblemCollection(int id) {
        URI uri = buildUrl(config.getCollectionsUrl() + "/{id}", singletonMap("id", id));
        return restTemplate.getForObject(uri, SybonProblemCollection.class);
    }

    @Override
    public SybonCompiler[] getCompilers() {
        URI uri = buildUrl(config.getCompilersUrl(), EMPTY_MAP);
        return restTemplate.getForObject(uri, SybonCompiler[].class);
    }

    private SybonSubmitResult[] getSubmitResults(int... ids) {
        String s = Arrays.stream(ids).mapToObj(Integer::toString)
                .collect(Collectors.joining(","));

        URI uri = buildUrl(config.getSubmitsUrl() + "/results", EMPTY_MAP, singletonMap("ids", s));
        return restTemplate.getForObject(uri, SybonSubmitResult[].class);
    }

    @Override
    public Submission.SubmissionResult[] getSubmissionResults(int... ids) {
        SybonSubmitResult[] results = getSubmitResults(ids);
        return Arrays.stream(results)
                .map(s -> new Submission.SubmissionResult(
                        s.getId(),
                        s.buildResult.getStatus() == SybonBuildResult.Status.OK,
                        s.buildResult.getOutput(),
                        Arrays.stream(s.getTestResults())
                                .map(r -> new Submission.SubmissionResult.TestGroupResult(
                                        r.getExecuted(),
                                        Arrays.stream(r.getTestResults())
                                                .map(res -> new Submission.SubmissionResult.TestResult(
                                                        res.getStatus().name(),
                                                        res.getJudgeMessage(),
                                                        res.getInput(),
                                                        res.getActualResult(),
                                                        res.getExpectedResult(),
                                                        res.getResourceUsage().getTimeUsageMillis(),
                                                        res.getResourceUsage().getMemoryUsageBytes()
                                                )).toArray(Submission.SubmissionResult.TestResult[]::new)
                                )).toArray(Submission.SubmissionResult.TestGroupResult[]::new)
                )).toArray(Submission.SubmissionResult[]::new);
    }

    private URI buildUrl(String url, Map<String, ?> urlParams, Map<String, ?> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        queryParams.forEach(builder::queryParam);
        builder.queryParam("api_key", config.getApiKey());

        return builder.buildAndExpand(urlParams).toUri();
    }

    private URI buildUrl(String url, Map<String, ?> urlParams) {
        return buildUrl(url, urlParams, EMPTY_MAP);
    }
}