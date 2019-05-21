package sh.platform.config.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;
import static java.util.Collections.singletonMap;
import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;

public class JSONBase64Provider implements ArgumentsProvider, AnnotationConsumer<JSONBase64> {

    private String file;

    @Override
    public void accept(JSONBase64 jsonBase64) {
        this.file = jsonBase64.value();
    }

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        URL url = JSONBase64Provider.class.getClassLoader().getResource(file);
        Path path = Paths.get(url.toURI());
        byte[] encode = Base64.getEncoder().encode(readAllBytes(path));
        Map<String, String> map = singletonMap(PLATFORM_RELATIONSHIPS.name(), new String(encode, StandardCharsets.UTF_8));
        return Stream.of(Arguments.of(map));
    }
}
