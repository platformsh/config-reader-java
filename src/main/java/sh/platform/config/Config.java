package sh.platform.config;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public interface Config {

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    Map<String, String> getVariables();

    /**
     * @return the available services
     */
    Map<String, Credential> getCredentials();

    /**
     * @return describes the routes
     */
    Map<String, Object> getRoutes();

    /**
     * @return values to {@link Map}
     */
    Map<PlatformVariables, String> toMap();

    static Config get() {
        return ApplicationSupplier.INSTANCE.get();
    }

    static Config get(Map<String, String> envs) {
        return new DefaultConfig(requireNonNull(envs, "envs is required"));
    }
}
