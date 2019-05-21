package sh.platform.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;

enum ApplicationSupplier implements Supplier<Application> {

    INSTANCE;

    {
        Map<String, String> env = new HashMap<>(System.getenv());
        env.computeIfAbsent(PLATFORM_RELATIONSHIPS.get(), (s) -> MapConverter.serviceToBase64());
        this.application = new DefaultApplication(env);
    }

    private final Application application;

    @Override
    public Application get() {
        return application;
    }
}
