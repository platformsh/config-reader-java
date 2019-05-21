package sh.platform.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sh.platform.config.PlatformVariables.PLATFORM_APPLICATION_NAME;
import static sh.platform.config.PlatformVariables.PLATFORM_APP_DIR;
import static sh.platform.config.PlatformVariables.PLATFORM_PROJECT;
import static sh.platform.config.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.PlatformVariables.PLATFORM_TREE_ID;
import static sh.platform.config.PlatformVariables.PLATFORM_VARIABLES;
import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;

class DefaultApplicationTest {


    @Test
    public void shouldReturnVariable() {
        Map<String, String> variables = getVariables();
        Application application = new DefaultApplication(variables);
        Map<PlatformVariables, String> map = application.toMap();
        assertEquals("dir", map.get(PLATFORM_APP_DIR));
        assertEquals("project", map.get(PLATFORM_PROJECT));
        assertEquals("tree", map.get(PLATFORM_TREE_ID));
        assertEquals("name", map.get(PLATFORM_APPLICATION_NAME));
        assertTrue(application.getRoutes().isEmpty());
        assertTrue(application.getServices().isEmpty());
    }

    @ParameterizedTest
    @JSONBase64("routes.json")
    public void shouldConvertRoutes(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Application application = new DefaultApplication(variables);
        Map<String, Object> routes = application.getRoutes();
        assertNotNull(routes);
        Map<String, Object> host = (Map<String, Object>) routes.get("http://host.com/");
        Assertions.assertEquals(true, host.get("restrict_robots"));
        Assertions.assertEquals("http://{default}/", host.get("original_url"));
        Assertions.assertEquals(false, host.get("primary"));
    }

    @ParameterizedTest
    @JSONBase64("variables.json")
    public void shouldConvertVariables(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_VARIABLES.get(), base64Text);
        Application application = new DefaultApplication(variables);
        Map<String, String> map = application.getVariables();
        Assertions.assertEquals("8", map.get("java.version"));
        Assertions.assertEquals("value", map.get("variable"));
    }

    @ParameterizedTest
    @JSONBase64("service.json")
    public void shouldConvertServices(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_RELATIONSHIPS.get(), base64Text);
        Application application = new DefaultApplication(variables);
        Map<String, Service> services = application.getServices();
        Assertions.assertFalse(services.isEmpty());
        Service database = services.get("database");
        Assertions.assertNotNull(database);

    }
    private Map<String, String> getVariables() {
        Map<String, String> variables = new HashMap<>();
        variables.put("ignore", "value");
        variables.put("ignore2", "value");
        variables.put(PLATFORM_APP_DIR.get(), "dir");
        variables.put(PLATFORM_PROJECT.get(), "project");
        variables.put(PLATFORM_TREE_ID.get(), "tree");
        variables.put(PLATFORM_APPLICATION_NAME.get(), "name");
        return variables;
    }


}