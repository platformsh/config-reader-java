package sh.platform.config.reader;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

final class MapConverter {

    private static final String SERVICE_JSON = "service.json";

    private static final Type SERVICE = new HashMap<String, List<Map<String, Object>>>() {
    }.getClass().getGenericSuperclass();

    private static final Type ROUTE = new HashMap<String, Object>() {
    }.getClass().getGenericSuperclass();

    private static final Type VARIABLES = new HashMap<String, String>() {
    }.getClass().getGenericSuperclass();

    private static final Gson GSON = new Gson();


    private MapConverter() {
    }

    static Map<String, List<Map<String, Object>>> toService(String relationship) {
        String text = new String(Base64.getDecoder().decode(relationship), UTF_8);
        return GSON.fromJson(text, SERVICE);
    }

    static Map<String, Object> toRoute(String routes) {
        String text = new String(Base64.getDecoder().decode(routes), UTF_8);
        return GSON.fromJson(text, ROUTE);
    }

    static Map<String, String> toVariable(String variable) {
        String text = new String(Base64.getDecoder().decode(variable), UTF_8);
        return GSON.fromJson(text, VARIABLES);
    }

    static String serviceToBase64() {
        try {
            InputStream stream = MapConverter.class.getClassLoader().getResourceAsStream(SERVICE_JSON);
            if (stream == null) {
                return null;
            }
            String result = new BufferedReader(new InputStreamReader(stream)).lines()
                    .collect(Collectors.joining());

            byte[] encode = Base64.getEncoder().encode(result.getBytes(StandardCharsets.UTF_8));
            return new String(encode, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new PlatformShException("An error when load the default configuration", e);
        }
    }
}
