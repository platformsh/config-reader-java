package sh.platform.config;

import java.util.function.Supplier;

/**
 * https://docs.platform.sh/development/variables.html
 */
public enum PlatformVariables implements Supplier<String> {
    /**
     * The absolute path to the application directory.
     */
    PLATFORM_APP_DIR,
    /**
     * A base64-encoded JSON object that describes the application.
     * It maps the content of the .platform.app.yaml that you have in Git and it has a few subkeys.
     */
    PLATFORM_APPLICATION,
    /**
     * The name of the application, as configured in the .platform.app.yaml file.
     */
    PLATFORM_APPLICATION_NAME,
    /**
     * The ID of the project.
     */
    PLATFORM_PROJECT,
    /**
     * The ID of the tree the application was built from.
     * It's essentially the SHA hash of the tree in Git.
     * If you need a unique ID for each build for whatever reason this is the value you should use.
     */
    PLATFORM_TREE_ID,
    /**
     * A base64-encoded JSON object which keys are variables names and values are variable values
     * (see below). Note that the values available in this structure may vary between build and runtime depending
     * on the variable type as described above.
     */
    PLATFORM_VARIABLES,
    /**
     * A random value created when the project is first created,
     * which is then stable throughout the project's life. This can be used for Drupal hash salt,
     * Symfony secret, or other similar values in other frameworks.
     */
    PLATFORM_PROJECT_ENTROPY,

    /**
     * The name of the Git branch.
     */
    PLATFORM_BRANCH,
    /**
     * The absolute path to the web document root, if applicable.
     */
    PLATFORM_DOCUMENT_ROOT,
    /**
     * The name of the environment generated by the name of the Git branch.
     */
    PLATFORM_ENVIRONMENT,
    /**
     * The SMTP host that email messages should be sent through.
     * This value will be empty if mail is disabled for the current environment.
     */
    PLATFORM_SMTP_HOST,
    /**
     * A base64-encoded JSON object whose keys are the relationship name and the values are arrays of relationship
     * endpoint definitions. See the documentation for each Service for details on each service type's schema.
     */
    PLATFORM_RELATIONSHIPS,
    /**
     * A base64-encoded JSON object that describes the routes that you defined in the environment.
     * It maps the content of the .platform/routes.yaml file.
     */
    PLATFORM_ROUTES;

    @Override
    public String get() {
        return this.name();
    }
}
