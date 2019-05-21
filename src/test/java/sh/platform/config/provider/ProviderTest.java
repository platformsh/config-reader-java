package sh.platform.config.provider;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.PlatformVariables;
import sh.platform.config.provider.JSONBase64;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProviderTest {

    @ParameterizedTest
    @JSONBase64("service.json")
    public void test(Map<String, String> map) {
        Assertions.assertNotNull(map);
        String relationship = map.get(PlatformVariables.PLATFORM_RELATIONSHIPS.get());
        Type type = new HashMap<String, List<Map<String, Object>>>() {
        }.getClass().getGenericSuperclass();
        String text = new String(Base64.getDecoder().decode(relationship), UTF_8);

        Jsonb jsonb = JsonbBuilder.create();
        Map<String, List<Map<String, Object>>> json = jsonb.fromJson(text, type);
        Assertions.assertNotNull(json);

    }
}
