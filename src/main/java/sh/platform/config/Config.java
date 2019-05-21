package sh.platform.config;

import java.util.Collections;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static sh.platform.config.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.PlatformVariables.PLATFORM_VARIABLES;

public class Config {

    private final Map<String, String> variables;

    private final Map<String, Object> routes;

    private final Map<PlatformVariables, String> envs;

    private final Map<String, Credential> services;

    Config(Map<String, String> envs) {
        this.variables = ofNullable(envs.get(PLATFORM_VARIABLES.get()))
                .map(MapConverter::toVariable).orElse(emptyMap());
        this.routes = ofNullable(envs.get(PLATFORM_ROUTES.get()))
                .map(MapConverter::toRoute).orElse(emptyMap());
        this.envs = PlatformVariables.toMap(envs);
        this.services = ServiceConverter.INSTANCE.apply(envs);

    }

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    public Map<String, String> getVariables() {
        return Collections.unmodifiableMap(variables);
    }

    /**
     * @return the available services
     */
    public Map<String, Credential> getCredentials() {
        return services;
    }

    /**
     * @return describes the routes
     */
    public Map<String, Object> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }

    /**
     * @return values to {@link Map}
     */
    public Map<PlatformVariables, String> toMap() {
        return Collections.unmodifiableMap(envs);
    }

    /**
     * @return a {@link Config} instance
     */
    public static Config get() {
        return ConfigSupplier.INSTANCE.get();
    }
}
