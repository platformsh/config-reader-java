package sh.platform.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides information a {@link HttpSolrClient}
 */
public class Solr extends Credential implements Supplier<HttpSolrClient> {

    public Solr(Map<String, Object> config) {
        super(config);
    }

    @Override
    public HttpSolrClient get() {

        final String path = getStringSafe("path").orElse("");
        String host = String.format("http://%s:%d/solr/%s", getHost(), getPort(), path);
        return new HttpSolrClient.Builder(host)
                .build();
    }
}
