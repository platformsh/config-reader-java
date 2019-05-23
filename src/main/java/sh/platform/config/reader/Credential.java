package sh.platform.config.reader;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class Credential {

    private final Map<String, Object> config;

    Credential(Map<String, Object> config) {
        this.config = config;
    }

    protected Map<String, Object> getConfig() {
        return config;
    }

    public String getName() {
        return toString("service");
    }

    public String getScheme() {
        return toString("scheme");
    }

    public String getIp() {
        return toString("ip");
    }

    public String getType() {
        return toString("type");
    }

    public int getPort() {
        return toInt("port");
    }

    public String getHost() {
        return toString("host");
    }

    public String getCluster() {
        return toString("cluster");
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(config);
    }

    public String getRelationship() {
        return toString("rel");
    }


    protected String toString(String key) {
        return getOptionalString(key)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found: " + key));
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
                .map(Double::parseDouble)
                .map(Double::intValue)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found: " + key));
    }

    public String toString() {
        return "Credential{" +
                "config=" + config +
                '}';
    }
}
