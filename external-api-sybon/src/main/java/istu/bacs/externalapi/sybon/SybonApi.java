package istu.bacs.externalapi.sybon;

import istu.bacs.db.problem.Problem;
import istu.bacs.db.submission.Submission;
import istu.bacs.db.submission.SubmissionResult;
import istu.bacs.externalapi.ExternalApi;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static istu.bacs.db.submission.SubmissionResult.withVerdict;
import static istu.bacs.db.submission.Verdict.NOT_SUBMITTED;
import static istu.bacs.db.submission.Verdict.PENDING;
import static istu.bacs.externalapi.ExternalApiHelper.addResource;
import static istu.bacs.externalapi.ExternalApiHelper.removeResource;
import static java.lang.String.format;
import static java.util.Collections.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
@Log
public class SybonApi implements ExternalApi {

    public static final String API_NAME = "SYBON";
    private static final SybonSubmitResultConverter submitResultConverter = new SybonSubmitResultConverter();
    private static final SybonLanguageConverter languageConverter = new SybonLanguageConverter();
    private static final SybonProblemConverter problemConverter = new SybonProblemConverter();
    private final SybonApiEndpointConfiguration config;
    private final RestTemplate restTemplate;

    public SybonApi(SybonApiEndpointConfiguration config, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Problem> getAllProblems() {
        String uri = buildUrl(config.getCollectionUrl(), singletonMap("id", 1));
        List<SybonProblem> problems = restTemplate.getForObject(uri, SybonProblemCollection.class).getProblems();
        return problems.stream()
                .map(problemConverter::convert)
                .collect(toList());
    }

    @Override
    public void submit(Submission submission) {
        SybonSubmit sybonSubmit = createSubmit(submission);

        String url = buildUrl(config.getSubmitUrl(), emptyMap());

        try {
            int submissionId = restTemplate.postForObject(url, sybonSubmit, int.class);
            submission.setExternalSubmissionId(withResourceName(submissionId));
            submission.setResult(withVerdict(submission, PENDING));
        } catch (Exception e) {
            log.warning(format("Unable to submit submission %d: %s", submission.getSubmissionId(), e.getMessage()));
            submission.setExternalSubmissionId(null);
            submission.setResult(withVerdict(submission, NOT_SUBMITTED));
        }
    }

    @Override
    public void submit(List<Submission> submissions) {
        List<SybonSubmit> sybonSubmits = submissions.stream()
                .map(this::createSubmit)
                .collect(toList());

        String url = buildUrl(config.getSubmitAllUrl(), emptyMap());
        int[] submissionIds = restTemplate.postForObject(url, sybonSubmits, int[].class);

        for (int i = 0; i < submissions.size(); i++)
            submissions.get(i).setExternalSubmissionId(withResourceName(submissionIds[i]));
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
    public void checkSubmissionResult(Submission submission) {
        checkSubmissionResult(singletonList(submission));
    }

    @Override
    public void checkSubmissionResult(List<Submission> submissions) {
        String ids = submissions.stream()
                .map(sub -> getSybonId(sub.getExternalSubmissionId()) + "")
                .collect(joining(","));

        String url = buildUrl(config.getResultsUrl(), emptyMap(), singletonMap("ids", ids));

        SybonSubmitResult[] sybonResults = restTemplate.getForObject(url, SybonSubmitResult[].class);
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);

            SubmissionResult result = submitResultConverter.convert(sybonResults[i]);
            result.setSubmissionResultId(submission.getResult().getSubmissionResultId());

            result.setSubmission(submission);
            submission.setResult(result);
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
    public String getApiName() {
        return API_NAME;
    }

    private String withResourceName(Object o) {
        return addResource(o, getApiName());
    }

    private int getSybonId(String s) {
        return Integer.parseInt(removeResource(s));
    }
}