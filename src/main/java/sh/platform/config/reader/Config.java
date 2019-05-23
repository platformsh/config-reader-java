package sh.platform.config.reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_APPLICATION_NAME;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_APP_DIR;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_BRANCH;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_DOCUMENT_ROOT;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_ENVIRONMENT;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_PROJECT;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_PROJECT_ENTROPY;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_RELATIONSHIPS;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_SMTP_HOST;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_TREE_ID;
import static sh.platform.config.reader.PlatformVariables.PLATFORM_VARIABLES;

/**
 * The object that provides access to the Platform.sh environment.
 */
public class Config {

    private final Map<String, String> variables;

    private final Map<String, Object> routes;

    private final Map<PlatformVariables, String> envs;

    private final Map<String, Credential> credentials;


    Config(Map<String, String> envs) {
        this.variables = ofNullable(envs.get(PLATFORM_VARIABLES.get()))
                .map(MapConverter::toVariable).orElse(emptyMap());
        this.routes = ofNullable(envs.get(PLATFORM_ROUTES.get()))
                .map(MapConverter::toRoute).orElse(emptyMap());
        this.envs = PlatformVariables.toMap(envs);
        this.credentials = ServiceConverter.INSTANCE.apply(envs);

    }

    /**
     * Creates a new instance from the environments values that come from {@link PlatformVariables}.
     */
    public Config() {
        this(getEnvironments());
    }

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    public Map<String, String> getVariables() {
        return Collections.unmodifiableMap(variables);
    }

    /**
     * @return the available credentials
     */
    public Map<String, Credential> getCredentials() {
        return credentials;
    }

    /**
     * @return describes the routes
     */
    public Map<String, Object> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }


    /**
     * @return @{@link PlatformVariables#PLATFORM_APPLICATION_NAME}
     */
    public String getApplicationName() {
        return toString(PLATFORM_APPLICATION_NAME);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_APP_DIR}
     */
    public String getAppDir() {
        return toString(PLATFORM_APP_DIR);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_PROJECT}
     */
    public String getProject() {
        return toString(PLATFORM_PROJECT);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_TREE_ID}
     */
    public String getTreeID() {
        return toString(PLATFORM_TREE_ID);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_PROJECT_ENTROPY}
     */
    public String getProjectEntropy() {
        return toString(PLATFORM_PROJECT_ENTROPY);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_BRANCH}
     */
    public String getBranch() {
        return toString(PLATFORM_BRANCH);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_DOCUMENT_ROOT}
     */
    public String getDocumentRoot() {
        return toString(PLATFORM_DOCUMENT_ROOT);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_SMTP_HOST}
     */
    public String getSmtpHost() {
        return toString(PLATFORM_SMTP_HOST);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_ENVIRONMENT}
     */
    public String getEnvironment() {
        return toString(PLATFORM_ENVIRONMENT);
    }


    /**
     * @return values to {@link Map}
     */
    public Map<PlatformVariables, String> toMap() {
        return Collections.unmodifiableMap(envs);
    }


    /**
     * A credential from a key
     *
     * @param key the key
     * @return a credential from the key
     */
    public Credential getCredential(String key) {
        Objects.requireNonNull(key, "key is required");
        return Optional.ofNullable(credentials.get(key))
                .orElseThrow(() -> new PlatformShException("Credential does not found: " + key));
    }

    /**
     * A credential from a key
     *
     * @param key the key
     * @return a credential from the key
     */
    public <T> T getCredential(String key, CredentialFormatter<T> formatter) {
        Objects.requireNonNull(key, "key is required");
        Objects.requireNonNull(formatter, "formatter is required");
        return Optional.ofNullable(credentials.get(key))
                .map(Credential::toMap)
                .map(formatter::apply)
                .orElseThrow(() -> new PlatformShException("Credential does not found: " + key));
    }


    private static Map<String, String> getEnvironments() {
        Map<String, String> envs = new HashMap<>(System.getenv());
        envs.computeIfAbsent(PLATFORM_RELATIONSHIPS.get(), (s) -> MapConverter.serviceToBase64());
        return envs;
    }

    private String toString(PlatformVariables variable) {
        return Optional
                .ofNullable(variables.get(variable.get()))
                .map(Object::toString)
                .orElseThrow(() -> new PlatformShException("Variable does not exist on environment: " + variable.get()));
    }
}
