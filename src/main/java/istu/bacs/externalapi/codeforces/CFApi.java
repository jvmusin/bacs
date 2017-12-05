package istu.bacs.externalapi.codeforces;

import istu.bacs.externalapi.ExternalApi;
import istu.bacs.externalapi.NumberHeadComparator;
import istu.bacs.model.Language;
import istu.bacs.model.Problem;
import istu.bacs.model.Submission;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Service
public class CFApi implements ExternalApi {

    public static final String API_NAME = "CF";

    private final RestTemplate restTemplate;
    private final CFProblemConverter problemConverter;

    public CFApi(RestTemplateBuilder restTemplateBuilder, CFProblemConverter problemConverter) {
        restTemplate = restTemplateBuilder.build();
        this.problemConverter = problemConverter;
    }

    @Override
    public Problem getProblem(String problemId) {
        return Optional.ofNullable(byName.get(problemId))
                .orElseThrow(() -> new IllegalArgumentException("Unknown problem: " + problemId));
    }

    private final Map<String, Problem> byName = new ConcurrentHashMap<>();
    List<Problem> getAllProblems() {
        String url = "http://codeforces.com/api/problemset.problems?lang=ru";
        CFResponse<CFGetProblemsResponseResult> resp = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<CFResponse<CFGetProblemsResponseResult>>() {}).getBody();

        if (resp.getStatus() == CFResponseStatus.FAILED) return emptyList();
        return resp.getResult().getProblems().stream()
                .map(problemConverter::convert)
                .peek(p -> byName.put(p.getProblemId(), p))
                .collect(toList());
    }

    @Override
    public void submit(boolean pretestsOnly, Submission submission) {

    }

    @Override
    public void submit(boolean pretestsOnly, List<Submission> submissions) {

    }

    @Override
    public void updateSubmissions(List<Submission> submissions) {

    }

    @Override
    public void updateProblems(List<Problem> problems) {
        problems.forEach(p -> {
            p.setDetails(getProblem(p.getProblemId()).getDetails());
            p.setComparator(NumberHeadComparator.getInstance());
        });
    }

    @Override
    public Set<Language> getSupportedLanguages() {
        return null;
    }

    @Override
    public String getResourceName() {
        return API_NAME;
    }
}