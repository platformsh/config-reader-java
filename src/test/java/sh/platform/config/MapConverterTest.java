package sh.platform.config;

import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapConverterTest {

    @ParameterizedTest
    @JSONBase64("service.json")
    public void shouldConvert(Map<String, String> map) {
        String relationship = map.get(PlatformVariables.PLATFORM_RELATIONSHIPS.get());
        Map<String, List<Map<String, Object>>> service = MapConverter.toService(relationship);
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
}