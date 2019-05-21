package sh.platform.config;

import java.util.Map;
import java.util.Optional;

public interface SQLDatabase extends Database {

    String getUserName();

    Optional<String> getFragment();

    boolean isPublic();

    Map<String, String> getQuery();

    String getPath();

    String getPassword();

    String getJDBCURL();


    static SQLDatabase get(String name) {
        Config config = Config.get();
        Credential credential = config.getCredentials().get(name);
        return new DefaultSQLDatabase(credential.toMap());
    }
}
