package sh.platform.config;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

final class MapConverter  {
    private static final Type SERVICE = new HashMap<String, List<Map<String, Object>>>() {
    }.getClass().getGenericSuperclass();

    private static final Type ROUTE = new HashMap<String, Object>() {
    }.getClass().getGenericSuperclass();

    private static final Jsonb JSONB = JsonbBuilder.create();

    private MapConverter() {
    }

    static Map<String, List<Map<String, Object>>> toService(String relationship){
        String text = new String(Base64.getDecoder().decode(relationship), UTF_8);
        return JSONB.fromJson(text, SERVICE);
    }
}
