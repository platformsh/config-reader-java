package sh.platform.config.reader;

import java.util.Map;
import java.util.function.Function;

public interface CredentialFormatter<T> extends Function<Map<String, Object>, T> {
}
