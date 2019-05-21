package sh.platform.config;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static sh.platform.config.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.PlatformVariables.PLATFORM_VARIABLES;

final class DefaultApplication implements Application {

    private final Map<String, String> variables;

    private final Map<String, Object> routes;

    private final Map<PlatformVariables, String> envs;

    DefaultApplication(Map<String, String> envs) {
        this.variables = ofNullable(envs.get(PLATFORM_VARIABLES.get()))
                .map(MapConverter::toVariable).orElse(emptyMap());
        this.routes = ofNullable(envs.get(PLATFORM_ROUTES.get()))
                .map(MapConverter::toRoute).orElse(emptyMap());
        this.envs = PlatformVariables.toMap(envs);

    }

    @Override
    public Map<String, String> getVariables() {
        return Collections.unmodifiableMap(variables);
    }

    @Override
    public Map<String, Service> getServices() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Object> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }

    @Override
    public Map<PlatformVariables, String> toMap() {
        return Collections.unmodifiableMap(envs);
    }
}
