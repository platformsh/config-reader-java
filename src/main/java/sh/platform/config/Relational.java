package sh.platform.config;

import java.util.Map;
import java.util.Optional;

public interface Relational extends Database {

    String getUserName();

    Optional<String> getFragment();

    boolean isPublic();

    Map<String, String> getQuery();

    String getPath();

    String getPassword();
}
