package sh.platform.config;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface Application {

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    Map<String, String> getVariables();

    /**
     * @return the available services
     */
    Map<String, Service> getServices();

    /**
     * @return describes the routes
     */
    Map<String, Object> getRoutes();

    /**
     * @return values to {@link Map}
     */
    Map<PlatformVariables, String> toMap();

    static Application get() {
        return ApplicationSupplier.INSTANCE.get();
    }

    static Application get(Map<String, String> envs) {
        return new DefaultApplication(requireNonNull(envs, "envs is required"));
    }
}
