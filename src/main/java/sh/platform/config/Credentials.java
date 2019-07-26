package sh.platform.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toMap;

final class Credentials implements Supplier<Map<String, Credential>> {

    private static final String SERVICE_JSON = "platform.properties";

    private final Map<String, Credential> credentials;

    private final Map<String, Object> properties;

    Credentials(Map<String, Credential> credentials) {
        this.credentials = credentials;
        this.properties = properties();
    }

    static Map<String, Object> properties() {
        try {
            InputStream stream = MapConverter.class.getClassLoader().getResourceAsStream(SERVICE_JSON);
            if (stream == null) {
                return Collections.emptyMap();
            }

            Properties properties = new Properties();
            properties.load(stream);

            Properties javaProperties = System.getProperties();
            javaProperties.keySet().stream().forEach(k -> properties.put(k, javaProperties.get(k)));
            return properties.keySet().stream()
                    .collect(toMap(k -> k.toString(), k -> properties.get(k)));
        } catch (Exception exp) {
            throw new FallbackException("An error when load the default configuration", exp);
        }
    }

    @Override
    public Map<String, Credential> get() {
        return credentials;
    }
}
