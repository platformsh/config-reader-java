package sh.platform.config.reader;

import java.util.Map;
import java.util.function.Function;

/**
 * In some cases the library being used to connect to a service wants its credentials formatted in a specific way;
 * it could be a DSN string of some sort or it needs certain values concatenated to the database name, etc.
 * For those cases you can use "Credential Formatters".
 * A Credential Formatter is a functional interface that takes a credentials array and returns any type, since the
 * library may want different types.
 *
 * @param <T> the produced type
 */
@FunctionalInterface
public interface CredentialFormatter<T> extends Function<Map<String, Object>, T> {
}
