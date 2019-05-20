package sh.platform.config;

import java.util.Map;
import java.util.Optional;

final class DefaultRelational extends DefaultService implements Relational {


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
        return (Map<String, String>) config.get("query");
    }

    @Override
    public String getPath() {
        return toString("path");
    }

    @Override
    public String getPassword() {
        return toString("password");
    }
}
