package sh.platform.config.reader;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;

final class MapConverter {

    private static final String SERVICE_JSON = "service.json";

    private static final Type SERVICE = new HashMap<String, List<Map<String, Object>>>() {
    }.getClass().getGenericSuperclass();

    private static final Type ROUTE = new HashMap<String, Object>() {
    }.getClass().getGenericSuperclass();

    private static final Type VARIABLES = new HashMap<String, String>() {
    }.getClass().getGenericSuperclass();

    private static final Jsonb JSONB = JsonbBuilder.create();


    private MapConverter() {
    }

    static Map<String, List<Map<String, Object>>> toService(String relationship) {
        String text = new String(Base64.getDecoder().decode(relationship), UTF_8);
        return JSONB.fromJson(text, SERVICE);
    }

    static Map<String, Object> toRoute(String routes) {
        String text = new String(Base64.getDecoder().decode(routes), UTF_8);
        return JSONB.fromJson(text, ROUTE);
    }

    static Map<String, String> toVariable(String variable) {
        String text = new String(Base64.getDecoder().decode(variable), UTF_8);
        return JSONB.fromJson(text, VARIABLES);
    }

    static String serviceToBase64() {
        try {
            URL url = MapConverter.class.getClassLoader().getResource(SERVICE_JSON);
            if (url == null) {
                return null;
            }
            Path path = Paths.get(url.toURI());
            byte[] encode = Base64.getEncoder().encode(readAllBytes(path));
            return new String(encode, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new PlatformShException("An error when load the default configuration", e);
        }
    }
}
