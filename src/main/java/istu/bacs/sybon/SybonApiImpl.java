package istu.bacs.sybon;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.EMPTY_MAP;
import static java.util.Collections.singletonMap;

@Service
public class SybonApiImpl implements SybonApi {

    private final SybonConfigurationProperties config;
    private final RestTemplate restTemplate;

    public SybonApiImpl(SybonConfigurationProperties config, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public SybonProblem getProblem(int id) {
        URI uri = buildUrl(config.getProblemsUrl() + "/{id}", singletonMap("id", id));
        return restTemplate.getForObject(uri, SybonProblem.class);
    }

    @Override
    public URI getStatementUrl(int problemId) {
        return buildUrl(config.getProblemsUrl() + "/{id}/statement", singletonMap("id", problemId));
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

    //todo: test it
    @Override
    public SybonSubmitResult[] getSubmitResults(String ids) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("ids", ids);
        URI uri = buildUrl(config.getSubmitsUrl() + "/results", EMPTY_MAP, queryParams);
        return restTemplate.getForObject(uri, SybonSubmitResult[].class);
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
