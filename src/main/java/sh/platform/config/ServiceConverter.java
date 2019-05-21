package sh.platform.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;

enum ServiceConverter implements Function<Map<String, String>, Map<String, Credential>> {

    INSTANCE;

    @Override
    public Map<String, Credential> apply(Map<String, String> envs) {
        Map<String, List<Map<String, Object>>> map = ofNullable(envs.get(PLATFORM_RELATIONSHIPS.get()))
                .map(MapConverter::toService).orElse(emptyMap());

        Map<String, Credential> services = new HashMap<>();

        for (Map.Entry<String, List<Map<String, Object>>> entry : map.entrySet()) {
            services.put(entry.getKey(), new DefaultCredential(flat(entry.getValue())));
        }

        return services;
    }

    private Map<String, Object> flat(List<Map<String, Object>> map) {
        if (map.isEmpty()) {
            return Collections.emptyMap();
        }
        return map.get(0);
    }
}
