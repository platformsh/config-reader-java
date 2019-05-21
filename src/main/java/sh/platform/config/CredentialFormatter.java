package sh.platform.config;

import java.util.Map;
import java.util.function.Function;

public interface CredentialFormatter<T> extends Function<Map<String, Object>, T> {
}
