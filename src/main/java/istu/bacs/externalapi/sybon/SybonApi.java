package istu.bacs.externalapi.sybon;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.model.SubmissionResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static istu.bacs.externalapi.ExternalApiHelper.addResource;
import static istu.bacs.externalapi.ExternalApiHelper.removeResource;
import static java.util.Collections.EMPTY_MAP;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
class SybonApi implements ExternalApi {

    public static final String API_NAME = "SYBON";

    private final SybonConfigurationProperties config;
    private final SybonProblemConverter problemConverter;
    private final SybonSubmitResultConverter submitResultConverter;
    private final SybonLanguageConverter languageConverter;
    private final RestTemplate restTemplate;

    public SybonApi(SybonConfigurationProperties config, SybonProblemConverter problemConverter, SybonSubmitResultConverter submitResultConverter, SybonLanguageConverter languageConverter, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.problemConverter = problemConverter;
        this.submitResultConverter = submitResultConverter;
        this.languageConverter = languageConverter;
        this.restTemplate = restTemplateBuilder.build();
    }

    private final Map<String, Problem> cache = new ConcurrentHashMap<>();
    @Override
    public Problem getProblem(String problemId) {
        return cache.computeIfAbsent(problemId, key -> {
            int id = getSybonId(problemId);
            URI uri = buildUrl(config.getProblemsUrl() + "/{id}", singletonMap("id", id));
            SybonProblem sybonProblem = restTemplate.getForObject(uri, SybonProblem.class);
            return problemConverter.convert(sybonProblem);
        });
    }

    @Override
    public URI getStatementUrl(String problemId) {
        int id = getSybonId(problemId);
        return buildUrl(config.getProblemsUrl() + "/{id}/statement", singletonMap("id", id));
    }

    @Override
    public void submit(boolean pretestsOnly, Submission submission) {
        SybonSubmit submit = createSubmit(pretestsOnly, submission);
        submission.setExternalSubmissionId(toResourceName(submit(submit)));
    }

    //todo: UNTESTED
    @Override
    public void submit(boolean pretestsOnly, List<Submission> submissions) {
        List<SybonSubmit> submits = submissions.stream()
                .map(sub -> createSubmit(pretestsOnly, sub))
                .collect(toList());
        int[] ids = submit(submits);
        for (int i = 0; i < submissions.size(); i++)
            submissions.get(i).setExternalSubmissionId(toResourceName(ids[i]));
    }

    private SybonSubmit createSubmit(boolean pretestsOnly, Submission submission) {
        SybonSubmit submit = new SybonSubmit();

        submit.setCompilerId(languageConverter.convert(submission.getLanguage()));
        submit.setSolution(Base64.getEncoder().encodeToString(submission.getSolution().getBytes()));
        submit.setSolutionFileType("Text");
        submit.setProblemId(getSybonId(submission.getProblem().getProblemId()));
        submit.setPretestsOnly(pretestsOnly);

        return submit;
    }

    private int submit(SybonSubmit submit) {
        URI url = buildUrl(config.getSubmitsUrl() + "/send", EMPTY_MAP);
        return restTemplate.postForObject(url, submit, int.class);
    }

    private int[] submit(List<SybonSubmit> submits) {
        URI url = buildUrl(config.getSubmitsUrl() + "/sendall", EMPTY_MAP);
        return restTemplate.postForObject(url, submits, int[].class);
    }

    @Override
    public void updateSubmissions(List<Submission> submissions) {
        String ids = submissions.stream()
                .map(sub -> getSybonId(sub.getExternalSubmissionId()) + "")
                .collect(joining(","));

        URI url = buildUrl(config.getSubmitsUrl() + "/results", EMPTY_MAP, singletonMap("ids", ids));

        SybonSubmitResult[] sybonResults = restTemplate.getForObject(url, SybonSubmitResult[].class);
        for (int i = 0; i < submissions.size(); i++) {
            SubmissionResult result = submitResultConverter.convert(sybonResults[i]);
            submissions.get(i).setResult(result);
        }
    }

    @Override
    public void updateProblemDetails(List<Problem> problems) {
        problems.forEach(p -> {
            Problem problem = getProblem(p.getProblemId());
            p.setDetails(problem.getDetails());
        });
    }

    @Override
    public Set<Language> getSupportedLanguages() {
        return languageConverter.getSupportedLanguages();
    }

    public SybonProblemCollection[] getProblemCollections(int offset, int limit) {
        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("Offset", offset);
        queryParams.put("Limit", limit);
        URI uri = buildUrl(config.getCollectionsUrl(), EMPTY_MAP, queryParams);
        return restTemplate.getForObject(uri, SybonProblemCollection[].class);
    }
    public SybonProblemCollection getProblemCollection(int id) {
        URI uri = buildUrl(config.getCollectionsUrl() + "/{id}", singletonMap("id", id));
        return restTemplate.getForObject(uri, SybonProblemCollection.class);
    }
    public SybonCompiler[] getCompilers() {
        URI uri = buildUrl(config.getCompilersUrl(), EMPTY_MAP);
        return restTemplate.getForObject(uri, SybonCompiler[].class);
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

    @Override
    public String getResourceName() {
        return API_NAME;
    }

    private String toResourceName(Object o) {
        return addResource(o, getResourceName());
    }

    private int getSybonId(String s) {
        return Integer.parseInt(removeResource(s));
    }
}