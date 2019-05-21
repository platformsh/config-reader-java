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
        Application application = Application.get();
        Service service = application.getServices().get(name);
        return new DefaultSQLDatabase(service.toMap());
    }
}
