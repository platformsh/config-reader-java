package sh.platform.config;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

class DefaultCredential implements Credential {

    private final Map<String, Object> config;

    DefaultCredential(Map<String, Object> config) {
        this.config = config;
    }

    protected Map<String, Object> getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return toString("service");
    }

    @Override
    public String getScheme() {
        return toString("scheme");
    }

    @Override
    public String getIp() {
        return toString("ip");
    }

    @Override
    public String getType() {
        return toString("type");
    }

    @Override
    public int getPort() {
        return toInt("port");
    }

    @Override
    public String getHost() {
        return toString("host");
    }

    @Override
    public String getCluster() {
        return toString("cluster");
    }

    @Override
    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(config);
    }

    @Override
    public String getRelationship() {
        return toString("rel");
    }


    protected String toString(String key) {
        return getOptionalString(key)
                .orElseThrow(() -> new PlatformShException("Key does not found: " + key));
    }

    protected Optional<String> getOptionalString(String key) {
        return Optional
                .ofNullable(config.get(key))
                .map(Object::toString);
    }

    protected Integer toInt(String key) {
        return Optional
                .ofNullable(config.get(key))
                .map(Object::toString)
                .map(Integer::parseInt)
                .orElseThrow(() -> new PlatformShException("Key does not found: " + key));
    }

    @Override
    public String toString() {
        return "Credential{" +
                "config=" + config +
                '}';
    }

}
