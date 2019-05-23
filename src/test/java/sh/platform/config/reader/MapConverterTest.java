package sh.platform.config.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.reader.provider.JSONBase64;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapConverterTest {

    @ParameterizedTest
    @JSONBase64("service.json")
    public void shouldConvertService(String base64Text) {
        Map<String, List<Map<String, Object>>> service = MapConverter.toService(base64Text);
        assertNotNull(service);

        Map<String, Object> database = service.get("database").get(0);
        assertEquals("database.internal", database.get("host"));
        assertEquals("246.0.97.91", database.get("ip"));
        assertEquals("main", database.get("path"));
        assertEquals(BigDecimal.valueOf(3306), database.get("port"));
        assertEquals("mysql", database.get("scheme"));
        assertEquals("user", database.get("username"));

        Map<String, Object> redis = service.get("redis").get(0);
        assertEquals("redis.internal", redis.get("host"));
        assertEquals("246.0.97.88", redis.get("ip"));
    }

    @ParameterizedTest
    @JSONBase64("routes.json")
    public void shouldConvertRoutes(String base64Text) {
        Map<String, Object> routes = MapConverter.toRoute(base64Text);
        Assertions.assertNotNull(routes);
        Map<String, Object> host = (Map<String, Object>) routes.get("http://host.com/");
        assertEquals(true, host.get("restrict_robots"));
        assertEquals("http://{default}/", host.get("original_url"));
        assertEquals(false, host.get("primary"));


    }
}