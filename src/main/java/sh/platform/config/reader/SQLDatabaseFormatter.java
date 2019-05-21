package sh.platform.config.reader;

import java.util.Map;
import java.util.Objects;

public class SQLDatabaseFormatter implements CredentialFormatter<SQLDatabase> {

    @Override
    public SQLDatabase apply(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        return new SQLDatabase(map);
    }
}
