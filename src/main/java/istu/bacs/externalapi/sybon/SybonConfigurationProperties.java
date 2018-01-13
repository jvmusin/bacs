package istu.bacs.externalapi.sybon;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@ConfigurationProperties(prefix = "sybon")
//@PropertySource("classpath:sybon.yml")
public class SybonConfigurationProperties {
    private String apiKey = "YBJY9zkkUUigNcYOlFoSg";

    private String collectionsUrl = "https://archive.sybon.org/api/Collections";
    private String getCollectionUrl = "{id}";

    private String submitsUrl = "https://checking.sybon.org/api/Submits";
    private String submitUrl = "send";
    private String submitAllUrl = "sendall";
    private String getResultsUrl = "results";

    public String getApiKey() {
        return apiKey;
    }

    public String getCollectionUrl() {
        return collectionsUrl + '/' + getCollectionUrl;
    }

    public String getSubmitUrl() {
        return submitsUrl + '/' + submitUrl;
    }

    public String getSubmitAllUrl() {
        return submitsUrl + "/" + submitAllUrl;
    }

    public String getResultsUrl() {
        return submitsUrl + '/' + getResultsUrl;
    }
}