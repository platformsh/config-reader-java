package sh.platform.config.reader;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SQLDatabase extends Database {


    private static final String URL = "jdbc:%s://%s:%d/%s";

    SQLDatabase(Map<String, Object> config) {
        super(config);
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

    static SQLDatabase get(String name) {
        Config config = Config.get();
        Credential credential = config.getCredentials().get(name);
        return new SQLDatabase(credential.toMap());
    }
}
