package sh.platform.config.reader;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A credential specialization that provides information to a SQL database.
 */
public class SQLDatabase extends Credential {


    private static final String URL = "jdbc:%s://%s:%d/%s";

    public SQLDatabase(Map<String, Object> config) {
        super(Objects.requireNonNull(config, "config is required"));
    }

    public String getUserName() {
        return toString("username");
    }

    public Optional<String> getFragment() {
        return getOptionalString("fragment");
    }

    public boolean isPublic() {
        return false;
    }

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

    public String getPath() {
        return toString("path");
    }

    public String getPassword() {
        return toString("password");
    }

    public String getJDBCURL() {
        return String.format(URL, getName(), getHost(), getPort(), getPath());
    }

}
