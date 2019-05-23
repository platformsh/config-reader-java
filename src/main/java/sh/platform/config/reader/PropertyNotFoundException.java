package sh.platform.config.reader;

public class PropertyNotFoundException extends PlatformShException {

    PropertyNotFoundException(String message) {
        super(message);
    }

    PropertyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
