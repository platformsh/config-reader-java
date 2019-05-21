package sh.platform.config;

import java.util.Map;
import java.util.Objects;

public class SQLDatabaseCredentialFormatter implements CredentialFormatter<SQLDatabase> {

    @Override
    public SQLDatabase apply(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        return new SQLDatabase(map);
    }
}
