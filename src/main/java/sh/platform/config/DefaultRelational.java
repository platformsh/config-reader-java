package sh.platform.config;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

final class DefaultRelational extends DefaultService implements Relational {


    private static final String URL = "jdbc:%s://%s:%d/%s";

    DefaultRelational(Map<String, Object> config) {
        super(config);
    }

    @Override
    public String getUserName() {
        return toString("username");
    }

    @Override
    public Optional<String> getFragment() {
        return getOptionalString("fragment");
    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public Map<String, String> getQuery() {
        Map<String, Object> config = getConfig();
        Map<String, Object> query = (Map<String, Object>) config.get("query");
        if (query == null) {
            return Collections.emptyMap();
        }
        return query.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().toString()));
    }

    @Override
    public String getPath() {
        return toString("path");
    }

    @Override
    public String getPassword() {
        return toString("password");
    }

    @Override
    public String getJDBCURL() {
        return String.format(URL, getName(), getHost(), getPort(), getPath());
    }
}
