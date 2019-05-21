package sh.platform.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;

enum ApplicationSupplier implements Supplier<Config> {

    INSTANCE;

    {
        Map<String, String> env = new HashMap<>(System.getenv());
        env.computeIfAbsent(PLATFORM_RELATIONSHIPS.get(), (s) -> MapConverter.serviceToBase64());
        this.config = new DefaultConfig(env);
    }

    private final Config config;

    @Override
    public Config get() {
        return config;
    }
}
