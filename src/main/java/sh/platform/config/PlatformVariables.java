package sh.platform.config;

import java.util.function.Supplier;

public enum PlatformVariables implements Supplier<String> {
    PLATFORM_APP_DIR,
    PLATFORM_APPLICATION,
    PLATFORM_APPLICATION_NAME,
    PLATFORM_PROJECT,
    PLATFORM_TREE_ID,
    PLATFORM_VARIABLES,
    PLATFORM_PROJECT_ENTROPY,
    PLATFORM_BRANCH,
    PLATFORM_DOCUMENT_ROOT,
    PLATFORM_ENVIRONMENT,
    PLATFORM_SMTP_HOST,
    PLATFORM_RELATIONSHIPS,
    PLATFORM_ROUTES;

    @Override
    public String get() {
        return this.name();
    }
}
