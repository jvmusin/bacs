package istu.bacs.externalapi.sybon;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.externalapi.NumberHeadComparator;
import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import istu.bacs.model.SubmissionResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static istu.bacs.externalapi.ExternalApiHelper.addResource;
import static istu.bacs.externalapi.ExternalApiHelper.removeResource;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
class SybonApi implements ExternalApi {

    public static final String API_NAME = "SYBON";

    private final SybonConfigurationProperties config;
    private final SybonSubmitResultConverter submitResultConverter;
    private final SybonLanguageConverter languageConverter;
    private final SybonProblemConverter problemConverter;
    private final RestTemplate restTemplate;

    public SybonApi(SybonConfigurationProperties config, SybonSubmitResultConverter submitResultConverter, SybonLanguageConverter languageConverter, SybonProblemConverter problemConverter, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.submitResultConverter = submitResultConverter;
        this.languageConverter = languageConverter;
        this.problemConverter = problemConverter;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Problem getProblem(String problemId) {
        String uri = buildUrl(config.getProblemsUrl() + "/{id}", singletonMap("id", getSybonId(problemId)));
        SybonProblem sybonProblem = restTemplate.getForObject(uri, SybonProblem.class);
        Problem problem = problemConverter.convert(sybonProblem);

        String statementUrl = buildUrl(sybonProblem.getStatementUrl(), emptyMap()); //with api_key
        problem.getDetails().setStatementUrl(statementUrl);

        return problem;
    }

    @Override
    public void submit(Submission submission, boolean pretestsOnly) {
        SybonSubmit submit = createSubmit(pretestsOnly, submission);
        submission.setExternalSubmissionId(withResourceName(submit(submit)));
    }

    //todo: UNTESTED
    @Override
    public void submit(List<Submission> submissions, boolean pretestsOnly) {
        List<SybonSubmit> submits = submissions.stream()
                .map(sub -> createSubmit(pretestsOnly, sub))
                .collect(toList());
        int[] ids = submit(submits);
        for (int i = 0; i < submissions.size(); i++)
            submissions.get(i).setExternalSubmissionId(withResourceName(ids[i]));
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
        String url = buildUrl(config.getSubmitsUrl() + "/send", emptyMap());
        return restTemplate.postForObject(url, submit, int.class);
    }

    private int[] submit(List<SybonSubmit> submits) {
        String url = buildUrl(config.getSubmitsUrl() + "/sendall", emptyMap());
        return restTemplate.postForObject(url, submits, int[].class);
    }

    @Override
    public void updateSubmissionDetails(List<Submission> submissions) {
        String ids = submissions.stream()
                .map(sub -> getSybonId(sub.getExternalSubmissionId()) + "")
                .collect(joining(","));

        String url = buildUrl(config.getSubmitsUrl() + "/results", emptyMap(), singletonMap("ids", ids));

        SybonSubmitResult[] sybonResults = restTemplate.getForObject(url, SybonSubmitResult[].class);
        for (int i = 0; i < submissions.size(); i++) {
            SubmissionResult result = submitResultConverter.convert(sybonResults[i]);
            submissions.get(i).setResult(result);
        }
    }

    @Override
    public void updateProblemDetails(List<Problem> problems) {
        problems.forEach(p -> {
            p.setDetails(getProblem(p.getProblemId()).getDetails());
            p.setComparator(NumberHeadComparator.getInstance());
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
        String uri = buildUrl(config.getCollectionsUrl(), emptyMap(), queryParams);
        return restTemplate.getForObject(uri, SybonProblemCollection[].class);
    }
    public SybonProblemCollection getProblemCollection(int id) {
        String uri = buildUrl(config.getCollectionsUrl() + "/{id}", singletonMap("id", id));
        return restTemplate.getForObject(uri, SybonProblemCollection.class);
    }
    public SybonCompiler[] getCompilers() {
        String uri = buildUrl(config.getCompilersUrl(), emptyMap());
        return restTemplate.getForObject(uri, SybonCompiler[].class);
    }

    private String buildUrl(String url, Map<String, ?> urlParams, Map<String, ?> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        queryParams.forEach(builder::queryParam);
        builder.queryParam("api_key", config.getApiKey());

        return builder.buildAndExpand(urlParams).toString();
    }

    private String buildUrl(String url, Map<String, ?> urlParams) {
        return buildUrl(url, urlParams, emptyMap());
    }

    @Override
    public String getResourceName() {
        return API_NAME;
    }

    private String withResourceName(Object o) {
        return addResource(o, getResourceName());
    }

    private int getSybonId(String s) {
        return Integer.parseInt(removeResource(s));
    }
}