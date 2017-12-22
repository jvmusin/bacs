package istu.bacs.externalapi.sybon;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.problem.Problem;
import istu.bacs.submission.Submission;
import istu.bacs.submission.SubmissionResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.List;
import java.util.Map;

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
    public List<Problem> getAllProblems() {
        String uri = buildUrl(config.getCollectionsUrl() + "/{id}", singletonMap("id", 1));
        List<SybonProblem> problems = restTemplate.getForObject(uri, SybonProblemCollection.class).getProblems();
        return problems.stream()
                .map(this::convertProblem)
                .collect(toList());
    }

    private Problem convertProblem(SybonProblem sybonProblem) {
        Problem problem = problemConverter.convert(sybonProblem);
        String statementUrl = buildUrl(sybonProblem.getStatementUrl(), emptyMap());
        problem.getDetails().setStatementUrl(statementUrl);
        return problem;
    }

    @Override
    public void submit(Submission submission) {
        SybonSubmit submit = createSubmit(submission);
        String url = buildUrl(config.getSubmitsUrl() + "/send", emptyMap());
        int submissionId = restTemplate.postForObject(url, submit, int.class);
        submission.setExternalSubmissionId(withResourceName(submissionId));
    }

    private SybonSubmit createSubmit(Submission submission) {
        SybonSubmit submit = new SybonSubmit();

        submit.setCompilerId(languageConverter.convert(submission.getLanguage()));
        submit.setSolution(Base64.getEncoder().encodeToString(submission.getSolution().getBytes()));
        submit.setSolutionFileType("Text");
        submit.setProblemId(getSybonId(submission.getProblem().getProblemId()));
        submit.setPretestsOnly(submission.isPretestsOnly());
        submit.setContinueCondition(SybonContinueCondition.Always);

        return submit;
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